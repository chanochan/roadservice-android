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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.roadservice.R;
import com.example.roadservice.backend.io.citizen.AddIssueRequest;
import com.example.roadservice.backend.threads.citizen.AddIssueThread;
import com.example.roadservice.models.GeoLocation;
import com.example.roadservice.models.Issue;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.lang.ref.WeakReference;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddIssueFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddIssueFragment extends Fragment {
    private static final int IMAGE_PICKER_REQUEST_CODE = 100;
    private Issue issue;
    private ImageView imageView;
    private TextView locationView;
    ActivityResultLauncher<Intent> imagePicker = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                Intent data = result.getData();
                Log.d("SHIT", "Read result");
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
        issue = new Issue(GeoLocation.ORIGIN, "", "", null);

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

        return view;
    }

    private void collectData() {
        View view = getView();
        assert view != null;
        EditText tmp = view.findViewById(R.id.titleText);
        issue.setTitle(tmp.getText().toString());
        tmp = view.findViewById(R.id.descriptionText);
        issue.setDescription(tmp.getText().toString());
    }

    private void submit() {
        collectData();
        // TODO add county view
        AddIssueRequest request = new AddIssueRequest(
                issue.getTitle(),
                issue.getDescription(),
                3,
                issue.getLocation().getLatitude(),
                issue.getLocation().getLongitude()
        );
        AddIssueThread thread = new AddIssueThread(handler, request);
        threadPoolExecutor.execute(thread);
    }

    private void updateInterface() {
        if (issue.getLocation().equals(GeoLocation.ORIGIN))
            locationView.setText(R.string.not_chosen);
        else
            locationView.setText(R.string.chosen);
        if (issue.getImageAddress() != null)
            Glide.with(this).load(issue.getImageAddress()).into(imageView);
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
        }
    }
}