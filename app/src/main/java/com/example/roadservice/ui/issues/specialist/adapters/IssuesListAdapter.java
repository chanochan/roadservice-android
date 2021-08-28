package com.example.roadservice.ui.issues.specialist.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.roadservice.R;
import com.example.roadservice.models.Issue;
import com.example.roadservice.ui.issues.specialist.IssuesListActivity;

import java.lang.ref.WeakReference;
import java.util.List;

public class IssuesListAdapter extends RecyclerView.Adapter<IssuesListAdapter.ViewHolder> {
    private final List<Issue> localDataSet;
    private final WeakReference<IssuesListActivity> target;

    public IssuesListAdapter(List<Issue> dataSet, IssuesListActivity issuesListActivity) {
        target = new WeakReference<>(issuesListActivity);
        localDataSet = dataSet;
    }

    @Override
    public IssuesListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.issue_row, viewGroup, false);

        return new IssuesListAdapter.ViewHolder(view, localDataSet, this);
    }

    @Override
    public void onBindViewHolder(IssuesListAdapter.ViewHolder viewHolder, final int position) {
        System.out.println(position);
        viewHolder.updateFields(localDataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleText;
        private final IssuesListAdapter IssuesListAdapter;

        public ViewHolder(View view, List<Issue> localDataSet, IssuesListAdapter issuesListAdapter) {
            super(view);
            this.IssuesListAdapter = issuesListAdapter;
            titleText = (TextView) view.findViewById(R.id.issueTitle);
            titleText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    issuesListAdapter.target.get().showIssueDetails(getLayoutPosition());
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
