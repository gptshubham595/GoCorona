package com.iitg.gocorona.aware;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iitg.gocorona.R;

public class AwarenessFragment2 extends Fragment {
    public AwarenessFragment2() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_awareness_fragment2, container, false);
        return view;
    }
}
