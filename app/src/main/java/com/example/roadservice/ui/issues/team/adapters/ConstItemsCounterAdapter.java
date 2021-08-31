package com.example.roadservice.ui.issues.team.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.roadservice.R;
import com.example.roadservice.ui.issues.specialist.structs.ItemCounter;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;

public class ConstItemsCounterAdapter extends RecyclerView.Adapter<ConstItemsCounterAdapter.ViewHolder> {
    private final String unit;
    private List<ItemCounter> localDataSet;

    public ConstItemsCounterAdapter(List<ItemCounter> dataSet, String unit) {
        localDataSet = dataSet;
        this.unit = unit;
    }

    @NotNull
    @Override
    public ConstItemsCounterAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_counter_row_const, viewGroup, false);

        return new ConstItemsCounterAdapter.ViewHolder(view, localDataSet, this);
    }

    @Override
    public void onBindViewHolder(ConstItemsCounterAdapter.ViewHolder viewHolder, final int position) {
        System.out.println(position);
        viewHolder.updateFields(localDataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public void setLocalDataSet(List<ItemCounter> localDataSet) {
        this.localDataSet = localDataSet;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ConstItemsCounterAdapter adapter;
        private final TextView titleText;
        private final TextView countText;
        private final TextView unitText;

        public ViewHolder(View view, List<ItemCounter> localDataSet, ConstItemsCounterAdapter adapter) {
            super(view);
            this.adapter = adapter;
            titleText = view.findViewById(R.id.counterItemTitle);
            countText = view.findViewById(R.id.counterItemCount);
            unitText = view.findViewById(R.id.counterUnitText);
        }

        public void updateFields(ItemCounter data) {
            titleText.setText(data.getObj().toString());
            countText.setText(toPersian(String.format(Locale.getDefault(), "%d", data.getCount())));
            unitText.setText(adapter.unit);
        }

        private String toPersian(String string) {
            String pat = "۰۱۲۳۴۵۶۷۸۹";
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < string.length(); i++) {
                result.append(pat.charAt(string.charAt(i) - '0'));
            }
            return result.toString();
        }
    }
}
