package com.example.roadservice.ui.issues.specialist.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.roadservice.R;
import com.example.roadservice.data.model.Issue;
import com.example.roadservice.ui.issues.specialist.PendingIssuesActivity;

import java.lang.ref.WeakReference;
import java.util.List;

public class PendingIssueAdapter extends RecyclerView.Adapter<PendingIssueAdapter.ViewHolder> {

    private final List<Issue> localDataSet;
    private final WeakReference<PendingIssuesActivity> target;

    public PendingIssueAdapter(List<Issue> dataSet, PendingIssuesActivity pendingIssuesActivity) {
        target = new WeakReference<>(pendingIssuesActivity);
        localDataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.pending_issue_row, viewGroup, false);

        return new ViewHolder(view, localDataSet, this);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        System.out.println(position);
        viewHolder.updateFields(localDataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleText;
        private final PendingIssueAdapter pendingIssueAdapter;

        public ViewHolder(View view, List<Issue> localDataSet, PendingIssueAdapter pendingIssueAdapter) {
            super(view);
            this.pendingIssueAdapter = pendingIssueAdapter;
            titleText = (TextView) view.findViewById(R.id.issueTitle);
            TextView acceptText = view.findViewById(R.id.acceptPendingBtn);
            TextView rejectText = view.findViewById(R.id.rejectPendingBtn);
            titleText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pendingIssueAdapter.target.get().showIssueDetails(getLayoutPosition());
                }
            });
            acceptText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pendingIssueAdapter.target.get().acceptIssue(getLayoutPosition());
                }
            });
            rejectText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pendingIssueAdapter.target.get().rejectIssue(getLayoutPosition());
                }
            });
        }

        public TextView getTextView() {
            return titleText;
        }

        public void updateFields(Issue data) {
            titleText.setText(data.getTitle());
        }
    }
}