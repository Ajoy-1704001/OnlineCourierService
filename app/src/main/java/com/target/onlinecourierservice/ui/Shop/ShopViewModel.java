package com.target.onlinecourierservice.ui.Shop;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShopViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public ShopViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Shop Fragment");
    }
    public LiveData<String> getText() {
        return mText;
    }
}