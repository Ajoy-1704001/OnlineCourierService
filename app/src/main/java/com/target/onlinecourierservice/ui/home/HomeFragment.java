package com.target.onlinecourierservice.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.target.onlinecourierservice.MainActivity;
import com.target.onlinecourierservice.R;
import com.target.onlinecourierservice.UserLoginActivity;
import com.target.onlinecourierservice.ui.CreateOrder.CreateOrderFragment;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    Button parcel_order,signOut;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });

        parcel_order=root.findViewById(R.id.parcel);
        signOut=root.findViewById(R.id.signOut);
        final NavController navController= Navigation.findNavController(getActivity(),R.id.nav_host_fragment);


        parcel_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment,new CreateOrderFragment())
                        .commit();*/
                navController.navigate(R.id.nav_create_order);
            }
        });

        mAuth=FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser();
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                goToLogin();
            }
        });

        return root;
    }

    private void goToLogin() {
        Intent LoginIntent=new Intent(getActivity(), UserLoginActivity.class);
        getActivity().startActivity(LoginIntent);
        getActivity().finish();
    }

}