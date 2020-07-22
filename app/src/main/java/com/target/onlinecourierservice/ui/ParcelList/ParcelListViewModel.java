package com.target.onlinecourierservice.ui.ParcelList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ParcelListViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public ParcelListViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is ParcelList Fragment");
    }
    public LiveData<String> getText() {
        return mText;
    }
}