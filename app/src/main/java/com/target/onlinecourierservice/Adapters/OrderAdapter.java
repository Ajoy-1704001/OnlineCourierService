package com.target.onlinecourierservice.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.target.onlinecourierservice.R;
import com.target.onlinecourierservice.model.CartDisplayLIst;
import com.target.onlinecourierservice.model.CartOrder;
import com.target.onlinecourierservice.model.CartProduct;
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

        holder.selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(mContext);
                bottomSheetDialog.setContentView(R.layout.order_data);
                bottomSheetDialog.setCanceledOnTouchOutside(true);

                final TextView id=(TextView)bottomSheetDialog.findViewById(R.id.id);
                final TextView sName=(TextView)bottomSheetDialog.findViewById(R.id.sName);
                final TextView sAddress=(TextView)bottomSheetDialog.findViewById(R.id.sAddress);
                final TextView sCity=(TextView)bottomSheetDialog.findViewById(R.id.sCity);
                final TextView sThana=(TextView)bottomSheetDialog.findViewById(R.id.sThana);
               final TextView total=(TextView)bottomSheetDialog.findViewById(R.id.total);
                final ImageView p1=(ImageView)bottomSheetDialog.findViewById(R.id.status_icon1);
                final ImageView p2=(ImageView)bottomSheetDialog.findViewById(R.id.status_icon2);
                final ImageView p3=(ImageView)bottomSheetDialog.findViewById(R.id.status_icon3);
                final ImageView p4=(ImageView)bottomSheetDialog.findViewById(R.id.status_icon4);
                final TextView s1=(TextView)bottomSheetDialog.findViewById(R.id.status1);
                final TextView s2=(TextView)bottomSheetDialog.findViewById(R.id.status2);
                final TextView s3=(TextView)bottomSheetDialog.findViewById(R.id.status3);
                final TextView s4=(TextView)bottomSheetDialog.findViewById(R.id.status4);
                final ArrayList<CartProduct>cartProducts=new ArrayList<>();
                RecyclerView recyclerView1=(RecyclerView)bottomSheetDialog.findViewById(R.id.recyclerView1);
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(mContext);
                if(recyclerView1!=null){
                    recyclerView1.setLayoutManager(linearLayoutManager);
                    final CartAdapter cartAdapter=new CartAdapter(cartProducts,mContext);
                    recyclerView1.setAdapter(cartAdapter);

                    final FirebaseDatabase database=FirebaseDatabase.getInstance();
                    final DatabaseReference databaseReference=database.getReference();
                    databaseReference.child("Purchase-Order").child(cartOrder.getId()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            CartOrder cartOrder1=snapshot.getValue(CartOrder.class);
                            id.setText(cartOrder.getId());
                            sName.setText(cartOrder1.getBuyerName());
                            sAddress.setText(cartOrder1.getShippingAddress());
                            sCity.setText(cartOrder1.getShippingCity());
                            sThana.setText(cartOrder1.getShippingThana());
                            total.setText(cartOrder1.getTotal_taka()+" /-");

                            if(cartOrder1.getStatus().equals("Pending")||cartOrder1.getStatus().equals("Verified")){
                                p1.setColorFilter(mContext.getResources().getColor(R.color.MainThemeColor));
                                s1.setTextColor(mContext.getResources().getColor(R.color.MainThemeColor));
                            }
                            else if(cartOrder1.getStatus().equals("Picked Up")){
                                p2.setColorFilter(mContext.getResources().getColor(R.color.MainThemeColor));
                                s2.setTextColor(mContext.getResources().getColor(R.color.MainThemeColor));
                            }
                            else if(cartOrder1.getStatus().equals("Shipped")){
                                p3.setColorFilter(mContext.getResources().getColor(R.color.MainThemeColor));
                                s3.setTextColor(mContext.getResources().getColor(R.color.MainThemeColor));
                            }
                            else if(cartOrder1.getStatus().equals("Delivered")){
                                p4.setColorFilter(mContext.getResources().getColor(R.color.MainThemeColor));
                                s4.setTextColor(mContext.getResources().getColor(R.color.MainThemeColor));
                            }

                            databaseReference.child("Purchase-Order").child(cartOrder.getId()).child("Products").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    cartProducts.clear();
                                    for (DataSnapshot keyNode:snapshot.getChildren()){
                                        CartProduct cartProduct=keyNode.getValue(CartProduct.class);
                                        cartProducts.add(cartProduct);
                                        cartAdapter.notifyDataSetChanged();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }



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
