package com.example.roadservice.ui.issues.citizen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.roadservice.R;
import com.example.roadservice.models.Issue;

public class RateIssueFragment extends Fragment {
    private Issue issue;

    public RateIssueFragment() {
        // Required empty public constructor
    }

    public static RateIssueFragment newInstance(Issue issue) {
        RateIssueFragment fragment = new RateIssueFragment();
        fragment.issue = issue;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rate_issue, container, false);
        Button btn = view.findViewById(R.id.submitRateBtn);
        btn.setOnClickListener(v -> {
            submit();
        });
        return view;
    }

    private void submit() {

    }
}