package com.anuraagpotdaar.trialskonnect.HelperClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.anuraagpotdaar.trialskonnect.R;

import java.util.ArrayList;

public class HealthDataReadingAdapter extends RecyclerView.Adapter<HealthDataReadingAdapter.HRViewHolder> {

    Context context;

    ArrayList<HealthDataModel> list;

    public HealthDataReadingAdapter(Context context, ArrayList<HealthDataModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public HRViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v  = LayoutInflater.from(context).inflate(R.layout.layout_heartrate,parent,false);
        return new HRViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HRViewHolder holder, int position) {
        HealthDataModel healthDataModel = list.get(position);
        holder.date.setText(healthDataModel.Date);
        holder.value.setText(healthDataModel.Value);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HRViewHolder extends RecyclerView.ViewHolder {

        TextView date,value;
        CardView card;
        public HRViewHolder (@NonNull View itemView) {
            super(itemView);
            value = itemView.findViewById(R.id.text_value);
            date = itemView.findViewById(R.id.text_date);

        }
    }
}
