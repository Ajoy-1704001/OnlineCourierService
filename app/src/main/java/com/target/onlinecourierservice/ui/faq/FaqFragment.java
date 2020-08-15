package com.target.onlinecourierservice.ui.faq;

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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.auth.FirebaseAuth;
import com.target.onlinecourierservice.R;
import com.target.onlinecourierservice.UserLoginActivity;

public class FaqFragment extends Fragment {

    private FaqViewModel faqViewModel;
    Button signOut;
    private FirebaseAuth mAuth;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        faqViewModel =
                ViewModelProviders.of(this).get(FaqViewModel.class);
        View root = inflater.inflate(R.layout.fragment_faq, container, false);
        //final TextView textView = root.findViewById(R.id.text_gallery);
        faqViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        mAuth=FirebaseAuth.getInstance();
        signOut=root.findViewById(R.id.signOut);
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