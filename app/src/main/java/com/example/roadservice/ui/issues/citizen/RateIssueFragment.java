package com.example.roadservice.ui.issues.citizen;

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
import android.widget.RatingBar;

import androidx.fragment.app.Fragment;

import com.example.roadservice.R;
import com.example.roadservice.backend.io.citizen.RateIssueRequest;
import com.example.roadservice.backend.io.citizen.RateIssueResponse;
import com.example.roadservice.backend.threads.citizen.RateIssueThread;
import com.example.roadservice.models.Issue;

import java.lang.ref.WeakReference;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class RateIssueFragment extends Fragment {
    private Issue issue;
    private ThreadPoolExecutor threadPoolExecutor;
    private RateIssueHandler handler;

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
        threadPoolExecutor = new ThreadPoolExecutor(
                0, 2, 15, TimeUnit.MINUTES, new LinkedBlockingQueue<>()
        );
        handler = new RateIssueHandler(Looper.getMainLooper(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rate_issue, container, false);
        Button btn = view.findViewById(R.id.submitRateBtn);
        btn.setOnClickListener(v -> submit());
        return view;
    }

    private void submit() {
        RatingBar ratingBar = this.getView().findViewById(R.id.issueRatingBar);
        RateIssueRequest request = new RateIssueRequest((int) ratingBar.getRating());
        RateIssueThread thread = new RateIssueThread(handler, request);
        threadPoolExecutor.execute(thread);
    }

    private void onDone() {
        CitizenDashboardActivity activity = (CitizenDashboardActivity) getActivity();
        activity.finish();
        startActivity(new Intent());
    }

    private static class RateIssueHandler extends Handler {
        private final WeakReference<RateIssueFragment> target;

        RateIssueHandler(Looper looper, RateIssueFragment target) {
            super(looper);
            this.target = new WeakReference<>(target);
        }

        @Override
        public void handleMessage(Message msg) {
            RateIssueFragment target = this.target.get();
            if (target == null)
                return;
            if (msg.arg1 != RateIssueResponse.CODE)
                return;
            // TODO handle login errors
            RateIssueResponse resp = (RateIssueResponse) msg.obj;
            if (resp == null) {
                Log.d("SHIT", "Empty response");
                return;
            }
            if (resp.status)
                target.onDone();
        }
    }
}