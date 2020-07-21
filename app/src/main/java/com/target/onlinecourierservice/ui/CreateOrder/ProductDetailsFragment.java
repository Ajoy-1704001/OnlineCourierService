package com.target.onlinecourierservice.ui.CreateOrder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.target.onlinecourierservice.Global;
import com.target.onlinecourierservice.R;



public class ProductDetailsFragment extends Fragment {

    Spinner packageTypeSpinner;
    ArrayAdapter<CharSequence>typeAdapter;
    RadioGroup sizeGroup,weightGroup;
    RadioButton s1,s2,s3,w1,w2,w3,w4,w5;

    public ProductDetailsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_product_details,container,false);

        packageTypeSpinner=v.findViewById(R.id.spinnerProductType);
        sizeGroup=v.findViewById(R.id.sizeGroup);
        s1=v.findViewById(R.id.size1);
        s2=v.findViewById(R.id.size2);
        s3=v.findViewById(R.id.size3);
        weightGroup=v.findViewById(R.id.weightGroup);
        w1=v.findViewById(R.id.w1);
        w2=v.findViewById(R.id.w2);
        w3=v.findViewById(R.id.w3);
        w4=v.findViewById(R.id.w4);
        w5=v.findViewById(R.id.w5);

        s1.setChecked(true);
        w1.setChecked(true);


        typeAdapter=ArrayAdapter.createFromResource(getActivity(),R.array.product_type,R.layout.spinner);
        typeAdapter.setDropDownViewResource(R.layout.spinner);
        packageTypeSpinner.setAdapter(typeAdapter);

        packageTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Global.packageType= (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        Global.sizeGroup=sizeGroup;
        Global.weightGroup=weightGroup;

        return v;
    }
}
