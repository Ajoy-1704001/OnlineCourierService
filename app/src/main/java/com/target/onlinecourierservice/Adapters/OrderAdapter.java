package com.target.onlinecourierservice.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.target.onlinecourierservice.R;
import com.target.onlinecourierservice.model.CartDisplayLIst;
import com.target.onlinecourierservice.model.CartOrder;
import com.target.onlinecourierservice.model.ParcelDisplay;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.Dataholder> {

    private ArrayList<CartDisplayLIst> parcelModels;
    private Context mContext;

    public OrderAdapter(ArrayList<CartDisplayLIst> parcelModels, Context mContext) {
        this.parcelModels = parcelModels;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public OrderAdapter.Dataholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.parcel_data, parent, false);
        return new Dataholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderAdapter.Dataholder holder, int position) {
        final CartDisplayLIst cartOrder = parcelModels.get(position);
        holder.SetID(cartOrder.getId());
        holder.SetDate(cartOrder.getDate());
        holder.SetStatus(cartOrder.getStatus());
        holder.bind(cartOrder.getStatus());
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
