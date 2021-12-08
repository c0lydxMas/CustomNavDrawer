package com.example.customnavdrawer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.customnavdrawer.fragment.BaseConverterFragment;
import com.example.customnavdrawer.fragment.CalculationFragment;
import com.example.customnavdrawer.fragment.LengthConverterFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int FRAGMENT_CALCULATOR = 0;
    private static final int FRAGMENT_BASE_CONVERTER = 1;
    private static final int FRAGMENT_LENGTH_CONVERTER = 2;

    private int mCurrentFragment = FRAGMENT_CALCULATOR;

    private DrawerLayout mDrawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.nav_drawer_open, R.string.nav_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        replaceFragment(new CalculationFragment());
        navigationView.getMenu().findItem(R.id.nav_calculator).setChecked(true);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.nav_calculator){
            if (mCurrentFragment != FRAGMENT_CALCULATOR){
                replaceFragment(new CalculationFragment());
                mCurrentFragment = FRAGMENT_CALCULATOR;
                navigationView.getMenu().findItem(R.id.nav_calculator).setChecked(true);
                navigationView.getMenu().findItem(R.id.nav_length_converter).setChecked(false);
                navigationView.getMenu().findItem(R.id.nav_base_converter).setChecked(false);
            }
        }else if(id == R.id.nav_base_converter){
            if (mCurrentFragment != FRAGMENT_BASE_CONVERTER){
                replaceFragment(new BaseConverterFragment());
                mCurrentFragment = FRAGMENT_BASE_CONVERTER;
                navigationView.getMenu().findItem(R.id.nav_calculator).setChecked(false);
                navigationView.getMenu().findItem(R.id.nav_length_converter).setChecked(false);
                navigationView.getMenu().findItem(R.id.nav_base_converter).setChecked(true);

            }
        }else if(id == R.id.nav_length_converter){
            if (mCurrentFragment != FRAGMENT_LENGTH_CONVERTER){
                replaceFragment(new LengthConverterFragment());
                mCurrentFragment = FRAGMENT_LENGTH_CONVERTER;
                navigationView.getMenu().findItem(R.id.nav_calculator).setChecked(false);
                navigationView.getMenu().findItem(R.id.nav_length_converter).setChecked(true);
                navigationView.getMenu().findItem(R.id.nav_base_converter).setChecked(false);
            }
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();
    }

}