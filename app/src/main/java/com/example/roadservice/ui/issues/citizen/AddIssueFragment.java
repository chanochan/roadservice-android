package com.example.roadservice.ui.issues.citizen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.roadservice.R;
import com.example.roadservice.backend.io.citizen.AddIssueRequest;
import com.example.roadservice.backend.io.citizen.AddIssueResponse;
import com.example.roadservice.backend.threads.citizen.AddIssueThread;
import com.example.roadservice.models.County;
import com.example.roadservice.models.Database;
import com.example.roadservice.models.GeoLocation;
import com.example.roadservice.models.Issue;
import com.example.roadservice.models.Province;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.textfield.TextInputLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddIssueFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddIssueFragment extends Fragment {
    private static final String TAG = "AddIssueFragment";
    private Issue issue;
    private ImageView imageView;
    private TextView locationView;
    ActivityResultLauncher<Intent> imagePicker = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                Intent data = result.getData();
                Log.d(TAG, "Read result");
                if (result.getResultCode() == Activity.RESULT_OK)
                    issue.setImageAddress(data.getDataString());
                else if (result.getResultCode() == ImagePicker.RESULT_ERROR)
                    issue.setImageAddress(null);
                updateInterface();
            }
    );
    ActivityResultLauncher<Intent> locationPicker = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        issue.setLocation(new GeoLocation(
                                data.getDoubleExtra("lat", 0),
                                data.getDoubleExtra("long", 0)
                        ));
                        updateInterface();
                    }
                }
            }
    );
    private ArrayList<Province> provinces;
    private ArrayList<County> counties;
    private Spinner provinceSpinner, countySpinner;
    private ArrayAdapter<String> provinceAdapter, countyAdapter;
    private AddIssueHandler handler;
    private ThreadPoolExecutor threadPoolExecutor;

    public AddIssueFragment() {
        // Required empty public constructor
    }

    public static AddIssueFragment newInstance() {
        return new AddIssueFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        issue = new Issue(0, GeoLocation.ORIGIN, "", "", null, 0);

        threadPoolExecutor = new ThreadPoolExecutor(
                0, 2, 15, TimeUnit.MINUTES, new LinkedBlockingQueue<>()
        );
        handler = new AddIssueHandler(Looper.getMainLooper(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_issue, container, false);

        locationView = view.findViewById(R.id.locationView);
        imageView = view.findViewById(R.id.issueImageView);
        Button changeLocationButton = view.findViewById(R.id.changeLocBtn);
        changeLocationButton.setOnClickListener(v ->
                locationPicker.launch(new Intent(
                        AddIssueFragment.this.getActivity(),
                        SelectLocationActivity.class
                ))
        );
        imageView.setOnClickListener(v -> ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(640, 480)
                .createIntent(intent -> {
                    imagePicker.launch(intent);
                    return null;
                })
        );

        Button addIssueButton = view.findViewById(R.id.addIssueBtn);
        addIssueButton.setOnClickListener(v -> submit());

        provinceSpinner = view.findViewById(R.id.provinceSpinner);
        provinceAdapter = new ArrayAdapter<>(
                getContext(),
                R.layout.spinner_item_province
        );
        provinceSpinner.setAdapter(provinceAdapter);
        provinceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateCounties(provinces.get(position).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        updateProvinces();

        countySpinner = view.findViewById(R.id.countySpinner);
        countyAdapter = new ArrayAdapter<>(
                getContext(),
                R.layout.spinner_item_province
        );
        countySpinner.setAdapter(countyAdapter);
        countySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                issue.setCounty(counties.get(position).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        updateCounties(provinces.get(0).getId());

        return view;
    }

    private void updateProvinces() {
        provinceAdapter.clear();
        provinces = Database.getProvinces();
        for (Province province : provinces)
            provinceAdapter.add(province.getName());
    }

    private void updateCounties(int id) {
        countyAdapter.clear();
        counties = Database.getProvinceCounties(id);
        for (County county : counties)
            countyAdapter.add(county.getName());
        countySpinner.setSelection(0);
    }

    private boolean collectData() {
        View view = getView();
        assert view != null;
        TextInputLayout titleLayout = view.findViewById(R.id.titleTextHolder);
        issue.setTitle(titleLayout.getEditText().getText().toString());
        TextInputLayout descriptionLayout = view.findViewById(R.id.descriptionTextLayout);
        issue.setDescription(descriptionLayout.getEditText().getText().toString());

        boolean isValid = true;
        if (issue.getLocation().equals(GeoLocation.ORIGIN))
            isValid = false;
        if (issue.getTitle().length() == 0) {
            titleLayout.setError(getString(R.string.error_issue_empty_title));
            isValid = false;
        } else
            titleLayout.setErrorEnabled(false);

        if (isValid)
            return true;
        return false;
    }

    private void submit() {
        if (!collectData()) return;
        AddIssueRequest request = new AddIssueRequest(
                issue.getTitle(),
                issue.getDescription(),
                issue.getCounty(),
                issue.getLocation().getLatitude(),
                issue.getLocation().getLongitude()
        );
        AddIssueThread thread = new AddIssueThread(handler, request);
        threadPoolExecutor.execute(thread);
    }

    private void updateInterface() {
        if (issue.getLocation().equals(GeoLocation.ORIGIN)) {
            locationView.setText(R.string.not_chosen);
            locationView.setTextColor(getResources().getColor(R.color.fail));
        } else {
            locationView.setText(R.string.chosen);
            locationView.setTextColor(getResources().getColor(R.color.success));
        }
        if (issue.getImageAddress() != null)
            Glide.with(this).load(issue.getImageAddress()).into(imageView);
    }

    private void onDone() {
        CitizenDashboardActivity activity = (CitizenDashboardActivity) getActivity();
        assert activity != null;
        activity.finish();
        startActivity(new Intent(activity, CitizenDashboardActivity.class));
    }

    private static class AddIssueHandler extends Handler {
        private final WeakReference<AddIssueFragment> target;

        AddIssueHandler(Looper looper, AddIssueFragment target) {
            super(looper);
            this.target = new WeakReference<>(target);
        }

        @Override
        public void handleMessage(Message msg) {
            AddIssueFragment target = this.target.get();
            if (target == null)
                return;
            if (msg.arg1 != AddIssueResponse.CODE)
                return;
            AddIssueResponse resp = (AddIssueResponse) msg.obj;
            if (resp != null && resp.status)
                target.onDone();
        }
    }
}