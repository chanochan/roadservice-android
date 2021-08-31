package com.example.roadservice.ui.issues.specialist.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.roadservice.R;
import com.example.roadservice.ui.issues.specialist.CreateMissionActivity;
import com.example.roadservice.ui.issues.specialist.structs.ItemCounter;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Locale;

public class ItemsCounterAdapter extends RecyclerView.Adapter<ItemsCounterAdapter.ViewHolder> {
    private final List<ItemCounter> localDataSet;
    private final WeakReference<CreateMissionActivity> target;

    public ItemsCounterAdapter(List<ItemCounter> dataSet, CreateMissionActivity createMissionActivity) {
        target = new WeakReference<>(createMissionActivity);
        localDataSet = dataSet;
    }

    @Override
    public ItemsCounterAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_counter_row, viewGroup, false);

        return new ItemsCounterAdapter.ViewHolder(view, localDataSet, this);
    }

    @Override
    public void onBindViewHolder(ItemsCounterAdapter.ViewHolder viewHolder, final int position) {
        System.out.println(position);
        viewHolder.updateFields(localDataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemsCounterAdapter ItemsCounterAdapter;
        private final TextView titleText;
        private final TextView countText;

        public ViewHolder(View view, List<ItemCounter> localDataSet, ItemsCounterAdapter itemsCounterAdapter) {
            super(view);
            this.ItemsCounterAdapter = itemsCounterAdapter;
            titleText = view.findViewById(R.id.counterItemTitle);
            countText = view.findViewById(R.id.counterItemCount);

            Button increaseBtn = view.findViewById(R.id.increaseBtn);
            increaseBtn.setOnClickListener(v -> {
                localDataSet.get(getLayoutPosition()).increase();
                itemsCounterAdapter.notifyItemChanged(getLayoutPosition());
            });

            Button decreaseBtn = view.findViewById(R.id.decreaseBtn);
            decreaseBtn.setOnClickListener(v -> {
                localDataSet.get(getLayoutPosition()).decrease();
                itemsCounterAdapter.notifyItemChanged(getLayoutPosition());
            });
        }

        public TextView getTextView() {
            return titleText;
        }

        public void updateFields(ItemCounter data) {
            titleText.setText(data.getObj().toString());
            countText.setText(toPersian(String.format(Locale.getDefault(), "%d", data.getCount())));
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
