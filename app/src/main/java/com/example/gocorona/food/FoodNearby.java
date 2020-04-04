package com.example.gocorona.food;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gocorona.R;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;

public class FoodNearby extends Fragment {
public FoodNearby(){}
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_food_nearby, container, false);

    }


}