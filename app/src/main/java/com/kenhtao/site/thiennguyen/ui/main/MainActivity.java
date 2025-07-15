package com.kenhtao.site.thiennguyen.ui.main;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kenhtao.site.thiennguyen.R;
import com.kenhtao.site.thiennguyen.base.BaseActivity;
import com.kenhtao.site.thiennguyen.ui.main.fragment.HomeFragment;
//import com.kenhtao.site.thiennguyen.ui.main.fragment.RecipientHomeFragment;
//import com.kenhtao.site.thiennguyen.ui.main.fragment.VolunteerHomeFragment;
//import com.kenhtao.site.thiennguyen.ui.main.fragment.ProfileFragment;

public class MainActivity extends BaseActivity {

    private String userRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        userRole = prefs.getString("user_role", "donor");

        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);

        bottomNavigation.setOnItemSelectedListener(this::onNavigationItemSelected);


        bottomNavigation.setSelectedItemId(R.id.nav_home);
    }

    private boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            selectedFragment = getHomeFragmentByRole(userRole);
        } else if (id == R.id.nav_explore) {
//            selectedFragment = new DiscoverFragment();
        } else if (id == R.id.nav_notify) {
//            selectedFragment = new NotificationFragment();
        } else if (id == R.id.nav_profile) {
//            selectedFragment = new ProfileFragment();
        }

        if (selectedFragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.containerMain, selectedFragment)
                    .commit();
            return true;
        }

        return false;
    }




    private Fragment getHomeFragmentByRole(String role) {
        switch (role) {
            case "recipient":
                return new HomeFragment();
            case "volunteer":
                return new HomeFragment();
            case "donor":
            default:
                return new HomeFragment();
        }
    }

}
