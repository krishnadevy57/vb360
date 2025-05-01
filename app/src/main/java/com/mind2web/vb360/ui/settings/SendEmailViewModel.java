package com.mind2web.vb360.ui.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SendEmailViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public SendEmailViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Logout");
    }

    public LiveData<String> getText() {
        return mText;
    }
}