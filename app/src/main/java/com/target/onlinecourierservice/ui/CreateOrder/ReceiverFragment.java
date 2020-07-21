package com.target.onlinecourierservice.ui.CreateOrder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.target.onlinecourierservice.Global;
import com.target.onlinecourierservice.R;

public class ReceiverFragment extends Fragment {

    Spinner spinnerCity,spinnerArea,spinnerThana;
    ArrayAdapter<CharSequence> cityAdapter;
    ArrayAdapter<CharSequence>thanaAdapter;
    //ArrayAdapter<CharSequence>areaAdapter;

    EditText name,phone,address,dIn;
    public ReceiverFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_receiver_details,container,false);


        name=v.findViewById(R.id.PersonName);
        phone=v.findViewById(R.id.PersonMobile);
        address=v.findViewById(R.id.PersonAddress);
        dIn=v.findViewById(R.id.deliveryInstruction);
        spinnerCity=v.findViewById(R.id.spinnerCity);
        spinnerThana=v.findViewById(R.id.spinnerThana);
        //spinnerArea=v.findViewById(R.id.spinnerArea);


        cityAdapter= ArrayAdapter.createFromResource(getActivity(),R.array.city_array,R.layout.spinner);
        cityAdapter.setDropDownViewResource(R.layout.spinner);
        spinnerCity.setAdapter(cityAdapter);

        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        thanaAdapter=ArrayAdapter.createFromResource(getActivity(),R.array.thana_array,R.layout.spinner);
                        Global.repCity= (String) parent.getItemAtPosition(position);
                        break;
                    case 1:
                        thanaAdapter=ArrayAdapter.createFromResource(getActivity(),R.array.Dhaka,R.layout.spinner);
                        Global.repCity= (String) parent.getItemAtPosition(position);
                        break;
                    case 2:
                        thanaAdapter=ArrayAdapter.createFromResource(getActivity(),R.array.Chittagong,R.layout.spinner);
                        Global.repCity= (String) parent.getItemAtPosition(position);
                        break;
                }
                thanaAdapter.setDropDownViewResource(R.layout.spinner);
                spinnerThana.setAdapter(thanaAdapter);
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
                Global.repThana= (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Global.repName=name;
        Global.repMobile=phone;
        Global.repAddress=address;
        Global.deliveyInstruction=dIn;





        return v;
    }
}
