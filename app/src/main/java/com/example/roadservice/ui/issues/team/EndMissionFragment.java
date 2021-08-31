package com.example.roadservice.ui.issues.team;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.roadservice.R;
import com.example.roadservice.backend.io.team.EndMissionRequest;
import com.example.roadservice.backend.io.team.EndMissionResponse;
import com.example.roadservice.backend.threads.team.EndMissionThread;
import com.google.android.material.textfield.TextInputLayout;

import java.lang.ref.WeakReference;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class EndMissionFragment extends Fragment {
    private EndMissionHandler handler;
    private ThreadPoolExecutor threadPoolExecutor;

    public EndMissionFragment() {
        // Required empty public constructor
    }

    public static EndMissionFragment newInstance() {
        EndMissionFragment fragment = new EndMissionFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        threadPoolExecutor = new ThreadPoolExecutor(
                0, 2, 15, TimeUnit.MINUTES, new LinkedBlockingQueue<>()
        );
        handler = new EndMissionHandler(Looper.getMainLooper(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_end_mission, container, false);

        Button endMissionButton = view.findViewById(R.id.endMissionBtn);
        endMissionButton.setOnClickListener(v -> submit());

        return view;
    }

    private void submit() {
        TextInputLayout input = this.getView().findViewById(R.id.reportTextLayout);
        String report = input.getEditText().getText().toString();
        EndMissionRequest req = new EndMissionRequest(report);
        EndMissionThread thread = new EndMissionThread(handler, req);
        threadPoolExecutor.execute(thread);
    }

    private void onDone() {
        // TODO refresh dashboard
    }

    private static class EndMissionHandler extends Handler {
        private final WeakReference<EndMissionFragment> target;

        EndMissionHandler(Looper looper, EndMissionFragment target) {
            super(looper);
            this.target = new WeakReference<>(target);
        }

        @Override
        public void handleMessage(Message msg) {
            EndMissionFragment target = this.target.get();
            if (target == null)
                return;
            if (msg.arg1 == EndMissionResponse.CODE) {
                EndMissionResponse resp = (EndMissionResponse) msg.obj;
                if (resp == null) {
                    Log.d("SHIT", "Empty response");
                    return;
                }
//                if (resp.status)
                target.onDone();
            }
        }
    }
}