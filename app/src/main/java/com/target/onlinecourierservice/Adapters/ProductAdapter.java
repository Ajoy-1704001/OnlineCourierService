package com.target.onlinecourierservice.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.FirebaseError;
import com.target.onlinecourierservice.Global;
import com.target.onlinecourierservice.R;
import com.target.onlinecourierservice.UserLoginActivity;
import com.target.onlinecourierservice.model.CartProduct;
import com.target.onlinecourierservice.model.ParcelDisplay;
import com.target.onlinecourierservice.model.ProductModel;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.Dataholdeer> {
    private ArrayList<ProductModel> productModels;
    private Context mContext;

    public ProductAdapter(ArrayList<ProductModel> productModels, Context mContext) {
        this.productModels = productModels;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ProductAdapter.Dataholdeer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.shop_product_data, parent, false);
        return new Dataholdeer(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.Dataholdeer holder, int position) {
        final ProductModel productModel = productModels.get(position);
        holder.SetName(productModel.getProductName());
        holder.SetManufacture(productModel.getProductMerchant());
        holder.SetPrice(productModel.getProductPrice());
        holder.bind(productModel.getProductImage());
        holder.box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(mContext);
                bottomSheetDialog.setContentView(R.layout.product_details);
                bottomSheetDialog.setCanceledOnTouchOutside(true);

                TextView pName=(TextView)bottomSheetDialog.findViewById(R.id.p_name);
                TextView pManu=(TextView)bottomSheetDialog.findViewById(R.id.manu);
                TextView pPrice=(TextView)bottomSheetDialog.findViewById(R.id.p_price);
                Button add=(Button)bottomSheetDialog.findViewById(R.id.add);
                final TextView Quantity=(TextView)bottomSheetDialog.findViewById(R.id.quantity);
                ImageView img=(ImageView)bottomSheetDialog.findViewById(R.id.p_image);
                ImageButton d=(ImageButton)bottomSheetDialog.findViewById(R.id.d);
                ImageButton i=(ImageButton)bottomSheetDialog.findViewById(R.id.i);

                d.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int p=Integer.parseInt(Quantity.getText().toString());
                        if(p>1){
                            p-=1;
                        }
                        Quantity.setText(String.valueOf(p));
                    }
                });
                i.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int p=Integer.parseInt(Quantity.getText().toString());
                        p+=1;
                        Quantity.setText(String.valueOf(p));
                    }
                });

                pName.setText(productModel.getProductName());
                pManu.setText(productModel.getProductMerchant());
                pPrice.setText(productModel.getProductPrice());
                Glide.with(mContext).load(productModel.getProductImage()).into(img);

                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       // Global.cartProducts.add(new CartProduct(productModel.getProductId(),productModel.getMerchantID(),productModel.getProductMerchant(),productModel.getProductName()));
                        mContext.startActivity(new Intent(mContext, UserLoginActivity.class));

                    }
                });



                bottomSheetDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return productModels == null? 0: productModels.size();
    }

    public class Dataholdeer extends RecyclerView.ViewHolder {
        private TextView txtName;
        private TextView txtManufacture;
        private TextView txtPrice;
        private ImageView imageView;
        private LinearLayout box;
        public Dataholdeer(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.product_name);
            txtManufacture = itemView.findViewById(R.id.manufacture);
            txtPrice = itemView.findViewById(R.id.product_price);
            imageView = itemView.findViewById(R.id.product_image);
            box=itemView.findViewById(R.id.box);
        }
        public void SetName(String name){
            txtName.setText(name);
        }
        public void SetManufacture(String name){
            txtManufacture.setText(name);
        }
        public void SetPrice(String name){
            txtPrice.setText(name);
        }
        public void bind(String name){
            Glide.with(mContext).load(name).into(imageView);
        }
    }
}
