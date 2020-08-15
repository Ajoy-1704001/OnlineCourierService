package com.target.onlinecourierservice.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.target.onlinecourierservice.R;
import com.target.onlinecourierservice.model.AssignModel;
import com.target.onlinecourierservice.model.DriverModel;
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
        final ParcelDisplay parcelModel = parcelModels.get(position);
        holder.SetID(parcelModel.getParcelID());
        holder.SetDate(parcelModel.getDate());
        holder.SetStatus(parcelModel.getStatus());
        holder.bind(parcelModel.getStatus());
        holder.selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(mContext);
                bottomSheetDialog.setContentView(R.layout.parcel_details);
                bottomSheetDialog.setCanceledOnTouchOutside(true);

                final TextView id=(TextView)bottomSheetDialog.findViewById(R.id.id);
                final TextView sName=(TextView)bottomSheetDialog.findViewById(R.id.sName);
                final TextView sMobile=(TextView)bottomSheetDialog.findViewById(R.id.sMobile);
                final TextView sAddress=(TextView)bottomSheetDialog.findViewById(R.id.sAddress);
                final TextView sCity=(TextView)bottomSheetDialog.findViewById(R.id.sCity);
                final TextView sThana=(TextView)bottomSheetDialog.findViewById(R.id.sThana);
                final TextView rName=(TextView)bottomSheetDialog.findViewById(R.id.rName);
                final TextView rMobile=(TextView)bottomSheetDialog.findViewById(R.id.rMobile);
                final TextView rAddress=(TextView)bottomSheetDialog.findViewById(R.id.rAddress);
                final TextView rCity=(TextView)bottomSheetDialog.findViewById(R.id.rCity);
                final TextView rThana=(TextView)bottomSheetDialog.findViewById(R.id.rThana);
                final LinearLayout delivery=(LinearLayout)bottomSheetDialog.findViewById(R.id.delivery);
                final TextView dmName=(TextView)bottomSheetDialog.findViewById(R.id.DmName);
                final TextView dmAction=(TextView)bottomSheetDialog.findViewById(R.id.DmAction);
                ImageButton callDm=(ImageButton)bottomSheetDialog.findViewById(R.id.callDm);
                final ImageView p1=(ImageView)bottomSheetDialog.findViewById(R.id.status_icon1);
                final ImageView p2=(ImageView)bottomSheetDialog.findViewById(R.id.status_icon2);
                final ImageView p3=(ImageView)bottomSheetDialog.findViewById(R.id.status_icon3);
                final ImageView p4=(ImageView)bottomSheetDialog.findViewById(R.id.status_icon4);
                final TextView s1=(TextView)bottomSheetDialog.findViewById(R.id.status1);
                final TextView s2=(TextView)bottomSheetDialog.findViewById(R.id.status2);
                final TextView s3=(TextView)bottomSheetDialog.findViewById(R.id.status3);
                final TextView s4=(TextView)bottomSheetDialog.findViewById(R.id.status4);

                FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                final DatabaseReference databaseReference=firebaseDatabase.getReference();
                databaseReference.child("Parcel").child(parcelModel.getParcelID()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ParcelModel parcelModel1=snapshot.getValue(ParcelModel.class);
                        if(parcelModel1!=null){
                            id.setText(parcelModel.getParcelID());
                            sName.setText(parcelModel1.getSenderName());
                            sMobile.setText(parcelModel1.getSenderPhone());
                            sAddress.setText(parcelModel1.getPickupAddress());
                            sCity.setText(parcelModel1.getPickupCity());
                            sThana.setText(parcelModel1.getPickupThana());
                            rName.setText(parcelModel1.getRecipientName());
                            rMobile.setText(parcelModel1.getRecipientPhone());
                            rAddress.setText(parcelModel1.getRecipientAddress());
                            rCity.setText(parcelModel1.getRecipientCity());
                            rThana.setText(parcelModel1.getRecipientThana());

                            if(parcelModel1.getStatus().equals("Pending")||parcelModel1.getStatus().equals("Verified")){
                                p1.setColorFilter(mContext.getResources().getColor(R.color.MainThemeColor));
                                s1.setTextColor(mContext.getResources().getColor(R.color.MainThemeColor));
                            }
                            else if(parcelModel1.getStatus().equals("Picked Up")){
                                p2.setColorFilter(mContext.getResources().getColor(R.color.MainThemeColor));
                                s2.setTextColor(mContext.getResources().getColor(R.color.MainThemeColor));
                            }
                            else if(parcelModel1.getStatus().equals("Shipped")){
                                p3.setColorFilter(mContext.getResources().getColor(R.color.MainThemeColor));
                                s3.setTextColor(mContext.getResources().getColor(R.color.MainThemeColor));
                            }
                            else if(parcelModel1.getStatus().equals("Delivered")){
                                p4.setColorFilter(mContext.getResources().getColor(R.color.MainThemeColor));
                                s4.setTextColor(mContext.getResources().getColor(R.color.MainThemeColor));
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                final String[] number = new String[1];
                databaseReference.child("Parcel-DeliveryMan").child(parcelModel.getParcelID()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            AssignModel assignModel=snapshot.getValue(AssignModel.class);
                            if(assignModel!=null){
                                if(assignModel.getAction().equals("Pick-Up")){
                                    dmAction.setText("Hey, I am on the way to pick up");
                                }
                                else {
                                    dmAction.setText("Hey, I am on the way to deliver");
                                }

                                databaseReference.child("Drivers").child(assignModel.getDriverId()).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        DriverModel driverModel=snapshot.getValue(DriverModel.class);
                                        if(driverModel!=null){
                                            dmName.setText(driverModel.getName());
                                            number[0] =driverModel.getPhoneNumber();
                                            delivery.setVisibility(View.VISIBLE);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                callDm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:"+number[0]));
                        mContext.startActivity(intent);
                    }
                });


                bottomSheetDialog.show();
            }
        });

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
        private LinearLayout selected;
        public Dataholder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.parcel_id);
            txtDate = itemView.findViewById(R.id.date);
            txtStatus = itemView.findViewById(R.id.status);
            imageView = itemView.findViewById(R.id.status_icon);
            selected= itemView.findViewById(R.id.selected);
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
            }else if(status.equals("Picked Up")){
                imageView.setImageResource(R.drawable.icons8_exercise_filled_100px);
            }else if(status.equals("Shipped")){
                imageView.setImageResource(R.drawable.icons8_shipped_filled_100px);
            }
            else if(status.equals("Delivered")){
                imageView.setImageResource(R.drawable.icons8_order_delivered_100px);
            }
            else {
                imageView.setImageResource(R.drawable.icons8_data_pending_filled_100px);
            }
        }

    }
}
