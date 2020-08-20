package com.target.onlinecourierservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.target.onlinecourierservice.Adapters.CartAdapter;
import com.target.onlinecourierservice.model.CartProduct;
import com.target.onlinecourierservice.model.ProductModel;

import java.io.File;
import java.util.ArrayList;

public class Cart extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<CartProduct> cartProducts = new ArrayList<>();
    CartAdapter cartAdapter;
    EditText name,address;
    Spinner spinnerCity,spinnerThana;
    ArrayAdapter<CharSequence> cityAdapter;
    ArrayAdapter<CharSequence>thanaAdapter;
    String City,Thana;
    Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        recyclerView = findViewById(R.id.recyclerView);
        name=findViewById(R.id.PersonName);
        address=findViewById(R.id.PersonAddress);
        spinnerCity=findViewById(R.id.spinnerCity);
        spinnerThana=findViewById(R.id.spinnerThana);
        next=findViewById(R.id.next);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Cart.this);
        recyclerView.setLayoutManager(linearLayoutManager);

        cartAdapter = new CartAdapter(Global.cartProducts, Cart.this);
        recyclerView.setAdapter(cartAdapter);

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                Global.cartProducts.remove(position);
                cartAdapter.notifyDataSetChanged();
            }

        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        cityAdapter= ArrayAdapter.createFromResource(Cart.this,R.array.city_array,R.layout.spinner);
        cityAdapter.setDropDownViewResource(R.layout.spinner);
        spinnerCity.setAdapter(cityAdapter);

        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        thanaAdapter=ArrayAdapter.createFromResource(Cart.this,R.array.thana_array,R.layout.spinner);
                        Global.PurCity= (String) parent.getItemAtPosition(position);
                        break;
                    case 1:
                        thanaAdapter=ArrayAdapter.createFromResource(Cart.this,R.array.Dhaka,R.layout.spinner);
                        Global.PurCity= (String) parent.getItemAtPosition(position);
                        break;
                    case  2:
                        thanaAdapter=ArrayAdapter.createFromResource(Cart.this,R.array.Chittagong,R.layout.spinner);
                        Global.PurCity= (String) parent.getItemAtPosition(position);
                        break;

                }
                thanaAdapter.setDropDownViewResource(R.layout.spinner);
                spinnerThana.setAdapter(thanaAdapter);
                if(Global.PurCity!=null){
                    if(Global.PurCity.equals("Select City")){
                        for(int i=0;i<Global.cartProducts.size();i++){
                            Global.cartProducts.get(i).setDeliveryCharge("0");
                            cartAdapter.notifyDataSetChanged();
                        }
                    }
                    else if(Global.PurCity.equals("Dhaka")){
                        for(int i=0;i<Global.cartProducts.size();i++){
                            Global.cartProducts.get(i).setDeliveryCharge("60");
                            cartAdapter.notifyDataSetChanged();
                        }
                    }
                    else {
                        for(int i=0;i<Global.cartProducts.size();i++){
                            Global.cartProducts.get(i).setDeliveryCharge("75");
                            cartAdapter.notifyDataSetChanged();
                        }
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        spinnerThana.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /*switch (position){
                    case 0:
                        areaAdapter=ArrayAdapter.createFromResource(getActivity(),R.array.Mirpur,R.layout.spinner);
                        Global.thana= (String) parent.getItemAtPosition(position);
                        break;
                    case 1:
                        areaAdapter=ArrayAdapter.createFromResource(getActivity(),R.array.Khilgaon,R.layout.spinner);
                        Global.thana= (String) parent.getItemAtPosition(position);
                        break;
                    case  2:
                        areaAdapter=ArrayAdapter.createFromResource(getActivity(),R.array.Chittagong,R.layout.spinner);
                        Global.thana= (String) parent.getItemAtPosition(position);
                        break;
                }
                areaAdapter.setDropDownViewResource(R.layout.spinner);
                spinnerArea.setAdapter(areaAdapter);*/
                Global.PurThana= (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name=name.getText().toString();
                String Address=address.getText().toString();

                if(Global.cartProducts.isEmpty()){
                    Toast.makeText(Cart.this,"Cart Is Empty",Toast.LENGTH_SHORT).show();
                }
                else if(Name.isEmpty()){
                    name.setError("Enter Name");
                }
                else if(Address.isEmpty()){
                    address.setError("Enter Full Address");
                }
                else if(Global.PurCity.isEmpty()){
                    Toast.makeText(Cart.this,"Select City",Toast.LENGTH_SHORT).show();
                }
                else if(Global.PurThana.isEmpty()){
                    Toast.makeText(Cart.this,"Select Thana",Toast.LENGTH_SHORT).show();
                }
                else {

                    int sum=0;
                    for(int i=0;i<Global.cartProducts.size();i++){
                        sum=sum+(Integer.parseInt(Global.cartProducts.get(i).getQuantity())*Integer.parseInt(Global.cartProducts.get(i).getPrice()))+Integer.parseInt(Global.cartProducts.get(i).getDeliveryCharge());
                    }
                    Intent intent=new Intent(Cart.this,PaymentActivity.class);
                    intent.putExtra("Total",String.valueOf(sum));
                    intent.putExtra("Name",Name);
                    intent.putExtra("Address",Address);
                    startActivity(intent);

                }
            }
        });



    }
}