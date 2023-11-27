package com.example.mentorfind;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.mentorfind.adapter.VIewPagerAdapter;
import com.example.mentorfind.fragment.SignInFragment;
import com.example.mentorfind.fragment.SignUpFragment;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private TabLayout mTabs;
    private VIewPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager=findViewById(R.id.viewPager);
        mTabs= findViewById(R.id.tabLayout);

        adapter= new VIewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SignInFragment(),"Sign In");
        adapter.addFragment(new SignUpFragment(),"Sign Up");
        mViewPager.setAdapter(adapter);
        mTabs.setupWithViewPager(mViewPager);


        mTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==0){
                    findViewById(R.id.layout_main).setBackground(getDrawable(R.drawable.layout_bg));
                    getWindow().setStatusBarColor(getColor(R.color.bkg));
                }
                if(tab.getPosition()==1){

                    findViewById(R.id.layout_main).setBackground(getDrawable(R.drawable.signup_bg));
                    getWindow().setStatusBarColor(getColor(R.color.purple));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}