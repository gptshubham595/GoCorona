package com.iitg.gocorona;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

public class Awareness extends AppCompatActivity {
    ViewPager viewPager;
    FragmentPagerItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_awareness);

        adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("SYMPTOMS", AwarenessFragment1.class)
                .add("PREVENTION", AwarenessFragment1.class)
                .add("TREATMENT", AwarenessFragment1.class)
                .create());
    }
}
