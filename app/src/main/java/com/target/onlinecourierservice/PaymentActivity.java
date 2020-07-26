package com.target.onlinecourierservice;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.target.onlinecourierservice.model.CartOrder;
import com.target.onlinecourierservice.model.ParcelModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PaymentActivity extends AppCompatActivity {
    Spinner payment_method;
    TextView totalMoney;
    EditText txID;
    ArrayAdapter<CharSequence> paymentAdapter;
    LinearLayout paymentView;
    Button complete;
    FirebaseAuth mAuth;
    FirebaseUser CurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        payment_method=findViewById(R.id.spinnerPaymentType);
        totalMoney=findViewById(R.id.total);
        paymentView=findViewById(R.id.payment_view);
        txID=findViewById(R.id.tId);
        complete=findViewById(R.id.next);

        String taka=getIntent().getStringExtra("Total");
        totalMoney.setText(taka);

        mAuth=FirebaseAuth.getInstance();
        CurrentUser=mAuth.getCurrentUser();

        paymentAdapter=ArrayAdapter.createFromResource(PaymentActivity.this,R.array.payment_method,R.layout.spinner);
        paymentAdapter.setDropDownViewResource(R.layout.spinner);
        payment_method.setAdapter(paymentAdapter);

        payment_method.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Global.pMethod=(String) parent.getItemAtPosition(position);

                if(Global.pMethod.equals("Cash On Delivery")){
                    paymentView.setVisibility(View.GONE);
                }
                else{
                    paymentView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=getIntent().getStringExtra("Name");
                String address=getIntent().getStringExtra("Address");
                String TotalAmount=getIntent().getStringExtra("Total");
                String City=Global.PurCity;
                String Thana=Global.PurThana;

                FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                final DatabaseReference databaseReference=firebaseDatabase.getReference();
                @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                String Date = df.format(Calendar.getInstance().getTime());
                final String unique= String.valueOf(System.currentTimeMillis());
                if(Global.pMethod.equals("Mobile Banking")){
                    if(txID.getText().toString().length()==10){
                        CartOrder cartOrder=new CartOrder(CurrentUser.getUid(),name,address,City,Thana,Date,TotalAmount,Global.pMethod,txID.getText().toString(),unique);
                        databaseReference.child("Purchase-Order").child(unique).setValue(cartOrder).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                databaseReference.child("Order-Status").child(unique).setValue("Pending");
                                for(int i=0;i<Global.cartProducts.size();i++){
                                    databaseReference.child("Purchase-Order").child(unique).child("Products").child("P" + String.valueOf(i + 1)).setValue(Global.cartProducts.get(i));
                                }
                                Intent intent=new Intent(PaymentActivity.this,ConfirmationActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        });
                    }
                    else{
                        Global.txID.setError("Invalid TxnID");
                    }
                }
                else if (Global.pMethod.equals("Cash On Delivery")){
                    CartOrder cartOrder=new CartOrder(CurrentUser.getUid(),name,address,City,Thana,Date,TotalAmount,"Cash On Delivery","XXXXXXXXXX",unique);
                    databaseReference.child("Purchase-Order").child(unique).setValue(cartOrder).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            databaseReference.child("Order-Status").child(unique).setValue("Pending");
                            for(int i=0;i<Global.cartProducts.size();i++){
                                databaseReference.child("Purchase-Order").child(unique).child("Products").child("P" + String.valueOf(i + 1)).setValue(Global.cartProducts.get(i));
                            }
                            Intent intent=new Intent(PaymentActivity.this,ConfirmationActivity.class);
                           intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    });

                }

            }
        });


    }
}