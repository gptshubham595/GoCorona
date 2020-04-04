package com.iitg.gocorona.covid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iitg.gocorona.R;

public class Main2Activity extends AppCompatActivity {

    Intent intent;
    String risk;
    TextView show;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2_covid);
        intent=getIntent();
        risk=intent.getStringExtra("risk");
        Double res=Double.parseDouble(risk);

        show=findViewById(R.id.show);
        layout=findViewById(R.id.layout);


        if(res<0.25)
        {
            show.setText("You have very low chance of being infected");
            layout.setBackgroundColor(Color.parseColor("#F9F37C"));
        }
        else if(res<0.5)
        {
            show.setText("You should stay safe and isolate yourself just for precautions.");
            layout.setBackgroundColor(Color.parseColor("#F5BC25"));
        }
        else if(res<0.75)
        {
            show.setText("You should see a doctor and get a test. You have a high chance of being infected.");
            layout.setBackgroundColor(Color.parseColor("#F65D06"));
        }
        else
        {
            show.setText("You are most probbaly infected. Go see a doctor along with your family and get in quarantine.");
            layout.setBackgroundColor(Color.parseColor("#FB081E"));
        }
    }
}
