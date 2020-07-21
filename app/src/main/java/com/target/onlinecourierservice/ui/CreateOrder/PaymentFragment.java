package com.target.onlinecourierservice.ui.CreateOrder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.target.onlinecourierservice.Global;
import com.target.onlinecourierservice.R;

public class PaymentFragment extends Fragment {
    Spinner payment_method;
    TextView totalMoney;
    EditText txID;
    ArrayAdapter<CharSequence>paymentAdapter;
    LinearLayout paymentView;
    public PaymentFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_payment_details,container,false);

        payment_method=v.findViewById(R.id.spinnerPaymentType);
        totalMoney=v.findViewById(R.id.totalTaka);
        paymentView=v.findViewById(R.id.payment_view);
        txID=v.findViewById(R.id.tId);

        int taka=0;
        int IntraCityCostBox=60;
        int InterCityCostBox=120;
        int IntraCityCostGlass=70;
        int InterCityCostGlass=130;
        int s1=0;
        int s2=10;
        int s3=20;
        int w1=0;
        int w2=5;
        int w3=10;
        int w4=15;
        int w5=20;

        if(Global.senderCity!=null){
            if(Global.senderCity.equals(Global.repCity)){
                if(Global.packageType.equals("Box Packaging")){
                    taka=taka+IntraCityCostBox;
                }
                else{
                    taka=taka+IntraCityCostGlass;
                }
            }else {
                if(Global.packageType.equals("Box Packaging")){
                    taka=taka+InterCityCostBox;
                }
                else{
                    taka=taka+InterCityCostGlass;
                }
            }


            if(Global.size.equals("s1")){
                taka=taka+s1;
            }
            else if(Global.size.equals("s2")){
                taka=taka+s2;
            }
            else if(Global.size.equals("s3")){
                taka=taka+s3;
            }

            if(Global.weight.equals("w1")){
                taka=taka+w1;
            }
            else if(Global.weight.equals("w2")){
                taka=taka+w2;
            }
            else if(Global.weight.equals("w3")){
                taka=taka+w3;
            }
            else if(Global.weight.equals("w4")){
                taka=taka+w4;
            }
            else if(Global.weight.equals("w5")){
                taka=taka+w5;
            }

        }

        totalMoney.setText(String.valueOf(taka));
        Global.totalMoney=String.valueOf(taka);

        paymentAdapter=ArrayAdapter.createFromResource(getActivity(),R.array.payment_method,R.layout.spinner);
        paymentAdapter.setDropDownViewResource(R.layout.spinner);
        payment_method.setAdapter(paymentAdapter);

        payment_method.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Global.paymentMethod=(String) parent.getItemAtPosition(position);

                if(Global.paymentMethod.equals("Cash On Delivery")){
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


        Global.txID=txID;

        return v;
    }
}
