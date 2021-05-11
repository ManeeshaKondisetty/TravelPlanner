package com.umkc.travelplanner;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.umkc.travelplanner.eat.EatFragment;
import com.umkc.travelplanner.explore.ExploreFragment;
import com.umkc.travelplanner.stay.HotelFragment;

public class MainActivity extends AppCompatActivity {
    public MainActivity() {
        super(R.layout.activity_main);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO - Start with Explore page
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_frame, new ExploreFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        BottomNavigationView bottom_bar_container = findViewById(R.id.bottom_navigation);
        bottom_bar_container.setItemIconTintList(null);
        bottom_bar_container.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);

                // TODO - Add Explore Click
                if (item.getItemId() == R.id.explore) {
                    final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_frame, new ExploreFragment());
                    transaction.addToBackStack(null);
                    transaction.commit();
                }

                // TODO - Add Eat Click
                if (item.getItemId() == R.id.eat) {
                    final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_frame, new EatFragment());
                    transaction.addToBackStack(null);
                    transaction.commit();
                }

                if (item.getItemId() == R.id.stay) {
                    final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_frame, new HotelFragment());
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                return false;
            }
        });
    }
}

