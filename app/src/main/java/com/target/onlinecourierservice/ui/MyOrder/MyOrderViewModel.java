package com.target.onlinecourierservice.ui.MyOrder;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyOrderViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public MyOrderViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}