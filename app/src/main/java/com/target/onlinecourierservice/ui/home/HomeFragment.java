package com.target.onlinecourierservice.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.target.onlinecourierservice.Adapters.ProductAdapter;
import com.target.onlinecourierservice.MainActivity;
import com.target.onlinecourierservice.R;
import com.target.onlinecourierservice.UserLoginActivity;
import com.target.onlinecourierservice.model.Merchant;
import com.target.onlinecourierservice.model.ProductAllData;
import com.target.onlinecourierservice.model.ProductModel;
import com.target.onlinecourierservice.ui.CreateOrder.CreateOrderFragment;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    Button parcel_order,signOut;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    RecyclerView recyclerView;
    ProductAdapter gridAdapter;
    ArrayList<ProductModel> productModels=new ArrayList<>();

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

        recyclerView=root.findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2);
        //gridLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(gridLayoutManager);

        final FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference=database.getReference();
        databaseReference.child("Merchant-Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productModels.clear();
                for(final DataSnapshot keyNode : snapshot.getChildren()){
                    final ProductAllData productAllData=keyNode.getValue(ProductAllData.class);
                    if(productAllData!=null){
                        databaseReference.child("Merchants").child(productAllData.getId()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Merchant merchant=snapshot.getValue(Merchant.class);
                                if(merchant.getBusinessName().equals("HasanTech")){
                                    productModels.add(new ProductModel(keyNode.getKey(),productAllData.getProduct_name(),merchant.getBusinessName(),productAllData.getId(),productAllData.getProduct_price(),productAllData.getImage_url(),productAllData.getProduct_description(),productAllData.getProduct_discount_price(),productAllData.getProduct_discount_percentage()));
                                    gridAdapter.notifyDataSetChanged();
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
        gridAdapter=new ProductAdapter(productModels,getActivity());
        recyclerView.setAdapter(gridAdapter);
        recyclerView.setHasFixedSize(true);


        return root;
    }

    private void goToLogin() {
        Intent LoginIntent=new Intent(getActivity(), UserLoginActivity.class);
        getActivity().startActivity(LoginIntent);
        getActivity().finish();
    }

}