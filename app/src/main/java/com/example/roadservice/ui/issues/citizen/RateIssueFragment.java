package com.example.roadservice.ui.issues.citizen;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.roadservice.R;
import com.example.roadservice.backend.io.citizen.RateIssueRequest;
import com.example.roadservice.backend.io.citizen.RateIssueResponse;
import com.example.roadservice.backend.threads.citizen.RateIssueThread;
import com.example.roadservice.models.Issue;
import com.example.roadservice.ui.RSAppCompatActivity;

import java.lang.ref.WeakReference;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class RateIssueFragment extends Fragment {
    private static final String TAG = "RateIssueFragment";
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
        view.findViewById(R.id.submitRateBtn).setOnClickListener(v -> submit());
        view.findViewById(R.id.discardRateBtn).setOnClickListener(v -> discard());
        return view;
    }

    private void submit() {
        RatingBar ratingBar = this.getView().findViewById(R.id.issueRatingBar);
        int rating = (int) ratingBar.getRating();
        if (rating == 0) {
            Toast.makeText(getContext(), getString(R.string.error_rating), Toast.LENGTH_SHORT).show();
            return;
        }
        RateIssueRequest request = new RateIssueRequest(rating);
        RateIssueThread thread = new RateIssueThread(handler, request);
        threadPoolExecutor.execute(thread);
    }

    private void discard() {
        Log.d(TAG, "Discard rating");
        RateIssueRequest request = new RateIssueRequest(null);
        RateIssueThread thread = new RateIssueThread(handler, request);
        threadPoolExecutor.execute(thread);
    }

    private void onDone() {
        RSAppCompatActivity activity = (RSAppCompatActivity) getActivity();
        activity.openDashboard();
    }

    private static class RateIssueHandler extends Handler {
        private static final String TAG = "RateIssueHandler";
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
            RateIssueResponse resp = (RateIssueResponse) msg.obj;
            if (resp == null) {
                Log.d(TAG, "Empty response");
                return;
            }
            Log.d(TAG, "Response status: " + resp.status);
            if (resp.status)
                target.onDone();
        }
    }
}