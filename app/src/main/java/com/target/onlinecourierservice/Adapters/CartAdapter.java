package com.target.onlinecourierservice.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.target.onlinecourierservice.R;
import com.target.onlinecourierservice.model.CartProduct;
import com.target.onlinecourierservice.model.ProductModel;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.Dataholder> {
    private ArrayList<CartProduct> cartProducts;
    private Context mContext;

    public CartAdapter(ArrayList<CartProduct> cartProducts, Context mContext) {
        this.cartProducts = cartProducts;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public CartAdapter.Dataholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.cartdetails, parent, false);
        return new Dataholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.Dataholder holder, int position) {
        CartProduct cartProduct=cartProducts.get(position);
        holder.SetName(cartProduct.getProductName());
        holder.SetQ("Qty: "+cartProduct.getQuantity());
        holder.SetPrice(String.valueOf(Integer.parseInt(cartProduct.getPrice())*Integer.parseInt(cartProduct.getQuantity()))+" /-");
        holder.SetD("(Delivery Charge) " +cartProduct.getDeliveryCharge()+" /-");
    }

    @Override
    public int getItemCount() {
        return cartProducts == null? 0: cartProducts.size();
    }

    public class Dataholder extends RecyclerView.ViewHolder {
        private TextView productName;
        private TextView quantity;
        private TextView productPrice;
        private TextView deliveryCharge;
        public Dataholder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.pName);
            quantity = itemView.findViewById(R.id.quantity);
            productPrice = itemView.findViewById(R.id.price);
            deliveryCharge = itemView.findViewById(R.id.deliveryCharge);
        }
        public void SetName(String Name){
            productName.setText(Name);
        }
        public void SetQ(String Q){
            quantity.setText(Q);
        }
        public void SetPrice(String p){
            productPrice.setText(p);
        }
        public void SetD(String D){
            deliveryCharge.setText(D);
        }

    }
}
