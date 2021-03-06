package com.example.sanfuproject.activity;

import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.RadioButton;

import com.example.sanfuproject.R;

import com.example.sanfuproject.activity.fragment.AccountFrag;
import com.example.sanfuproject.activity.fragment.CartFrag;
import com.example.sanfuproject.activity.fragment.ClassifyFrag;
import com.example.sanfuproject.activity.fragment.HomeFrag;
import com.example.sanfuproject.activity.fragment.LeftFrag;
import com.example.sanfuproject.activity.fragment.RightFrag;

import static com.example.sanfuproject.activity.utils.Constants.drawerLayout;

public class MainActivity extends AppCompatActivity {

    private Fragment content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDrawerLayout();
        if ("购物车".equals(getIntent().getStringExtra("cart"))) {
            RadioButton radioButton = (RadioButton) findViewById(R.id.radioButton3);
            setDefault(new CartFrag(), radioButton);
        } else {
            RadioButton radioButton = (RadioButton) findViewById(R.id.radioButton1);
            setDefault(new HomeFrag(), radioButton);
        }
    }

    private void initDrawerLayout() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.LEFT);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.LEFT);
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        getSupportFragmentManager().beginTransaction().add(R.id.left_frame, new LeftFrag()).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.right_frame, new RightFrag()).commit();
    }

    private void setDefault(Fragment fragment, RadioButton radioButton) {
        radioButton.setChecked(true);
        getSupportFragmentManager().beginTransaction().add(R.id.framelayout, fragment).commit();
        content = fragment;
    }

    public void change(View view) {
        Fragment fragment = null;
        switch (view.getId()) {
            case R.id.radioButton1:
                fragment = new HomeFrag();
                break;
            case R.id.radioButton2:
                fragment = new ClassifyFrag();
                break;
            case R.id.radioButton3:
                fragment = new CartFrag();
                break;
            case R.id.radioButton4:
                fragment = new AccountFrag();
                break;
        }
        load(fragment);
    }

    private void load(Fragment fragment) {
        if (content != fragment) {
            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, fragment).commit();
            content = fragment;
        }
    }
}
