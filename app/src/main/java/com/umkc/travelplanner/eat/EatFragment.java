package com.umkc.travelplanner.eat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.umkc.travelplanner.R;

public class EatFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //TODO change to your layout
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_hotel, null);
        //TODO findViewById's here
          return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        //TODO Your logic here
    }
}
