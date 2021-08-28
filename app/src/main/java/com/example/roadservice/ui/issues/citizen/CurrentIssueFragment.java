package com.example.roadservice.ui.issues.citizen;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.roadservice.R;
import com.example.roadservice.models.Issue;

import java.lang.ref.WeakReference;

public class CurrentIssueFragment extends Fragment {
    private Issue issue;

    public CurrentIssueFragment() {
        // Required empty public constructor
    }

    public static CurrentIssueFragment newInstance(Issue issue) {
        CurrentIssueFragment fragment = new CurrentIssueFragment();
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
        View view = inflater.inflate(R.layout.fragment_current_issue, container, false);

        TextView titleTextView = view.findViewById(R.id.myIssueTitleText);
        titleTextView.setText(issue.getTitle());

        TextView descriptionTextView = view.findViewById(R.id.myIssueDescText);
        descriptionTextView.setText(issue.getDescription());
        return view;
    }
}