package com.example.roadservice.ui.issues.citizen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.roadservice.R;
import com.example.roadservice.data.model.GeoLocation;
import com.example.roadservice.data.model.Issue;

public class AddIssueActivity extends AppCompatActivity {
    private Issue issue;
    private TextView locationView;
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
                        System.out.println(issue.getLocation().getLatitude());
                        System.out.println(issue.getLocation().getLongitude());
                        updateInterface();
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_issue);

        issue = new Issue(GeoLocation.ORIGIN, "", "");

        locationView = findViewById(R.id.locationView);
        Button changeLocationButton = findViewById(R.id.changeLocBtn);
        changeLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationPicker.launch(new Intent(
                        AddIssueActivity.this,
                        SelectLocationActivity.class
                ));
            }
        });

        this.updateInterface();
    }

    private void updateInterface() {
        if (issue.getLocation().equals(GeoLocation.ORIGIN))
            locationView.setText(R.string.not_chosen);
        else
            locationView.setText(R.string.chosen);
    }

    public void finish(View view) {
        finish();
    }
}