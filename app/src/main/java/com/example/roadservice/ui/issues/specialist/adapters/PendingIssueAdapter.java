package com.example.roadservice.ui.issues.specialist.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.roadservice.R;
import com.example.roadservice.models.Issue;
import com.example.roadservice.ui.issues.specialist.SpecialistDashboardActivity;

import java.lang.ref.WeakReference;
import java.util.List;

public class PendingIssueAdapter extends RecyclerView.Adapter<PendingIssueAdapter.ViewHolder> {

    private final List<Issue> localDataSet;
    private final WeakReference<SpecialistDashboardActivity> target;

    public PendingIssueAdapter(List<Issue> dataSet, SpecialistDashboardActivity specialistDashboardActivity) {
        target = new WeakReference<>(specialistDashboardActivity);
        localDataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.issue_row, viewGroup, false);

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
        private final TextView descriptionText;
        private final ImageView imageView;
        private final PendingIssueAdapter pendingIssueAdapter;

        public ViewHolder(View view, List<Issue> localDataSet, PendingIssueAdapter pendingIssueAdapter) {
            super(view);
            this.pendingIssueAdapter = pendingIssueAdapter;
            titleText = (TextView) view.findViewById(R.id.issueTitle);
            descriptionText = (TextView) view.findViewById(R.id.issueRowDescription);
            imageView = (ImageView) view.findViewById(R.id.issueRowImage);

            view.setOnClickListener(v -> pendingIssueAdapter.target.get().showIssueDetails(getLayoutPosition()));
        }

        public TextView getTextView() {
            return titleText;
        }

        public void updateFields(Issue data) {
            titleText.setText(data.getTitle());
            descriptionText.setText(data.getDescription());
            Glide.with(pendingIssueAdapter.target.get())
                    .load(data.getImageAddress())
                    .into(imageView);
        }
    }
}