package com.placereminder.placereminder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.placereminder.placereminder.R;
import com.placereminder.placereminder.model.Reminderdb;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder> {

    private Context context;
    private List<Reminderdb> reminderdbArrayList;

    public ReminderAdapter(Context context, List<Reminderdb> reminderdbArrayList) {
        this.context = context;
        this.reminderdbArrayList = reminderdbArrayList;
    }

    @NonNull
    @Override
    public ReminderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reminder_item_layout, parent, false);
        return new ReminderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReminderViewHolder holder, int position) {
        holder.tvPlaceName.setText(reminderdbArrayList.get(position).getPlaceName());
        holder.tvPlaceLocation.setText(reminderdbArrayList.get(position).getPlaceLocation());
        holder.tvDate.setText(reminderdbArrayList.get(position).getDate());
        holder.tvTime.setText(reminderdbArrayList.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return reminderdbArrayList.size();
    }

    public class ReminderViewHolder extends RecyclerView.ViewHolder {
        TextView tvPlaceName,tvPlaceLocation,tvDate, tvTime;

        public ReminderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPlaceName = itemView.findViewById(R.id.tv_place_name);
            tvPlaceLocation = itemView.findViewById(R.id.tv_place_location);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvTime = itemView.findViewById(R.id.tv_time);

        }
    }
}
