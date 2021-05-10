package com.umkc.travelplanner.eat;

import android.util.Log;

import androidx.lifecycle.ViewModel;

public class EatViewModel extends ViewModel {

    private String mFood;
    private String mPlace;
    private CustomAdaptor mAdaptor;
    private boolean flag = true;

    public EatViewModel() {
        Log.d("EAT", "EatViewModel: is created viewmodel");
    }

    public String getFood() {
        return mFood;
    }

    public void setFood(String food) {
        mFood = food;
    }

    public String getPlace() {
        return mPlace;
    }

    public void setPlace(String place) {
        mPlace = place;
    }

    public CustomAdaptor getAdaptor() {
        return mAdaptor;
    }

    public void setAdaptor(CustomAdaptor adaptor) {
        mAdaptor = adaptor;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d("EAT", "onCleared: viewmodel");
    }
}
