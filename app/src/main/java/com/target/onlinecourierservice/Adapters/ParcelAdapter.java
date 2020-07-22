package com.target.onlinecourierservice.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.target.onlinecourierservice.R;
import com.target.onlinecourierservice.model.ParcelDisplay;
import com.target.onlinecourierservice.model.ParcelModel;

import java.util.ArrayList;

public class ParcelAdapter extends RecyclerView.Adapter<ParcelAdapter.Dataholder> {

    private ArrayList<ParcelDisplay> parcelModels;
    private Context mContext;

    public ParcelAdapter(ArrayList<ParcelDisplay> parcelModels, Context mContext) {
        this.parcelModels = parcelModels;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ParcelAdapter.Dataholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.parcel_data, parent, false);
        return new Dataholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParcelAdapter.Dataholder holder, int position) {
        ParcelDisplay parcelModel = parcelModels.get(position);
        holder.SetID(parcelModel.getParcelID());
        holder.SetDate(parcelModel.getDate());
        holder.SetStatus(parcelModel.getStatus());
        holder.bind(parcelModel.getStatus());

    }

    @Override
    public int getItemCount() {
        return parcelModels == null? 0: parcelModels.size();
    }

    public class Dataholder extends RecyclerView.ViewHolder {
        private TextView txtName;
        private TextView txtDate;
        private ImageView imageView;
        private TextView txtStatus;
        public Dataholder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.parcel_id);
            txtDate = itemView.findViewById(R.id.date);
            txtStatus = itemView.findViewById(R.id.status);
            imageView = itemView.findViewById(R.id.status_icon);
        }
        public void SetID(String name) {
            txtName.setText(name);
        }

        public void SetDate(String Date) {
            txtDate.setText(Date);
        }

        public void SetStatus(String status) {
            txtStatus.setText(status);
        }

        public void bind(String status){
            if(status.equals("Pending")){
                imageView.setImageResource(R.drawable.icons8_data_pending_filled_100px);
            }else if(status.equals("Active")){
                imageView.setImageResource(R.drawable.icons8_exercise_filled_100px);
            }else if(status.equals("Shipped")){
                imageView.setImageResource(R.drawable.icons8_shipped_filled_100px);
            }
            else if(status.equals("Delivered")){
                imageView.setImageResource(R.drawable.icons8_order_delivered_100px);
            }
        }
    }
}
