package com.target.onlinecourierservice.ui.CreateOrder;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.target.onlinecourierservice.Global;
import com.target.onlinecourierservice.R;
import com.target.onlinecourierservice.ViewPageAdapter;

public class CreateOrderFragment extends Fragment {

    private CreateOrderViewModel slideshowViewModel;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPageAdapter viewPageAdapter;
    Button next;
    ImageView s1,s2,s3,s4;

    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(CreateOrderViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_create_order, container, false);
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });


        tabLayout=root.findViewById(R.id.tabMode);
        viewPager=root.findViewById(R.id.viewer);
        next=root.findViewById(R.id.next);
        s1=root.findViewById(R.id.s1);
        s2=root.findViewById(R.id.s2);
        s3=root.findViewById(R.id.s3);
        s4=root.findViewById(R.id.s4);
        viewPageAdapter=new ViewPageAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        viewPageAdapter.AddFragment(new SenderFragment(),"Sender");
        viewPageAdapter.AddFragment(new ReceiverFragment(),"Receiver");
        viewPageAdapter.AddFragment(new ProductDetailsFragment(),"Product");
        viewPageAdapter.AddFragment(new PaymentFragment(),"Payment");
        viewPager.setAdapter(viewPageAdapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0);

        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tabLayout.getSelectedTabPosition()==0){
                    if(Global.senderName.getText().toString().length()==0){
                        Snackbar.make(root,"Please Enter Sender Name",Snackbar.LENGTH_SHORT).show();
                        Global.senderName.setError("Please Enter Sender Name");
                    }
                    else if(Global.senderMobile.getText().toString().length()==0){
                        Snackbar.make(root,"Please Enter Sender Mobile Number",Snackbar.LENGTH_SHORT).show();
                        Global.senderMobile.setError("Please Enter Sender Mobile Number");
                    }
                    else if(Global.senderAddress.getText().toString().length()==0){
                        Snackbar.make(root,"Please Enter PickUp Address",Snackbar.LENGTH_SHORT).show();
                        Global.senderAddress.setError("Please Enter PickUp Address");
                    }
                    else if(Global.senderCity.equals("Select City")){
                        Snackbar.make(root,"Please Select City",Snackbar.LENGTH_SHORT).show();
                    }
                    else if(Global.senderThana.equals("Select Thana")){
                        Snackbar.make(root,"Please Select Thana",Snackbar.LENGTH_SHORT).show();
                    }
                    else{
                        viewPager.setCurrentItem(1);
                        s1.setImageResource(R.drawable.icons8_user_location_100px_1);
                        s2.setImageResource(R.drawable.icons8_address_100px);

                    }
                }
                else if(tabLayout.getSelectedTabPosition()==1){
                    if(Global.repName.getText().toString().length()==0){
                        Snackbar.make(root,"Please Enter Sender Name",Snackbar.LENGTH_SHORT).show();
                        Global.repName.setError("Please Enter Sender Name");
                    }
                    else if(Global.repMobile.getText().toString().length()==0){
                        Snackbar.make(root,"Please Enter Sender Mobile Number",Snackbar.LENGTH_SHORT).show();
                        Global.repMobile.setError("Please Enter Sender Mobile Number");
                    }
                    else if(Global.repAddress.getText().toString().length()==0){
                        Snackbar.make(root,"Please Enter PickUp Address",Snackbar.LENGTH_SHORT).show();
                        Global.repAddress.setError("Please Enter PickUp Address");
                    }
                    else if(Global.repCity.equals("Select City")){
                        Snackbar.make(root,"Please Select City",Snackbar.LENGTH_SHORT).show();
                    }
                    else if(Global.repThana.equals("Select Thana")){
                        Snackbar.make(root,"Please Select Thana",Snackbar.LENGTH_SHORT).show();
                    }
                    else{
                        viewPager.setCurrentItem(2);
                        s2.setImageResource(R.drawable.icons8_address_100px_2);
                        s3.setImageResource(R.drawable.icons8_product_100px_1);

                    }
                }
                else if(tabLayout.getSelectedTabPosition()==2){

                    int size=Global.sizeGroup.getCheckedRadioButtonId();
                    int weight=Global.weightGroup.getCheckedRadioButtonId();
                    if(size==R.id.size1){
                        Global.size="s1";
                    }
                    else if(size==R.id.size2){
                        Global.size="s2";
                    }
                    else if(size==R.id.size3){
                        Global.size="s3";
                    }

                    if (weight==R.id.w1){
                        Global.weight="w1";
                    }
                    else if (weight==R.id.w2){
                        Global.weight="w2";
                    }
                    else if (weight==R.id.w3){
                        Global.weight="w3";
                    }
                    else if (weight==R.id.w4){
                        Global.weight="w4";
                    }
                    else if (weight==R.id.w5){
                        Global.weight="w5";
                    }
                    viewPager.setCurrentItem(3);
                    s3.setImageResource(R.drawable.icons8_product_100px);
                    s4.setImageResource(R.drawable.icons8_mobile_payment_100px_1);
                    next.setText("Request PickUp");
                }
            }
        });



        return root;
    }
    /*@Override
    //////back dile jiggesh korbo j do you want o cancel order? emon ekta dialog
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }*/

}