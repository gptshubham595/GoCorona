package com.iitg.gocorona.covid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.iitg.gocorona.R;

public class MainActivity extends AppCompatActivity {

    LinearLayout layout1,layout2,layout3,layout4,layout5,layout6,layout7,layout8;
    Button submit1,submit2,submit3,submit4,submit5,submit6,submit7,submit8;
    CheckBox input2check1, input2check2, input2check3, input3check1, input3check2, input3check3, input3check4,
    input4check1, input4check2, input4check3, input4check4, input4check5, input4check6, input5check1,
    input5check2, input5check3, input5check4, input5check5, input5check6, input5check7, input6check1,
    input6check2, input6check3, input6check4, input7check1, input7check2, input7check3, input7check4;
    CheckBox input7check5, input7check6, input7check7, input7check8, input8check1, input8check2, input8check3,
            input8check4;
    ScrollView scroll;

    EditText input1;
    int  age;
    Double risk=0.0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_covid);

        getSupportActionBar().setTitle("Symptom checker");

        scroll=findViewById(R.id.scroll);

        layout1=findViewById(R.id.layout1);
        layout2=findViewById(R.id.layout2);
        layout3=findViewById(R.id.layout3);
        layout4=findViewById(R.id.layout4);
        layout5=findViewById(R.id.layout5);
        layout6=findViewById(R.id.layout6);
        layout7=findViewById(R.id.layout7);
        layout8=findViewById(R.id.layout8);

        submit1=findViewById(R.id.submit1);
        submit2=findViewById(R.id.submit2);
        submit3=findViewById(R.id.submit3);
        submit4=findViewById(R.id.submit4);
        submit5=findViewById(R.id.submit5);
        submit6=findViewById(R.id.submit6);
        submit7=findViewById(R.id.submit7);
        submit8=findViewById(R.id.submit8);

        input1=findViewById(R.id.input1);
        input2check1=findViewById(R.id.input2check1);
        input2check2=findViewById(R.id.input2check2);
        input2check3=findViewById(R.id.input2check3);
        input3check1=findViewById(R.id.input3check1);
        input3check2=findViewById(R.id.input3check2);
        input3check3=findViewById(R.id.input3check3);
        input3check4=findViewById(R.id.input3check4);
        input4check1=findViewById(R.id.input4check1);
        input4check2=findViewById(R.id.input4check2);
        input4check3=findViewById(R.id.input4check3);
        input4check4=findViewById(R.id.input4check4);
        input4check5=findViewById(R.id.input4check5);
        input4check6=findViewById(R.id.input4check6);
        input5check1=findViewById(R.id.input5check1);
        input5check2=findViewById(R.id.input5check2);
        input5check3=findViewById(R.id.input5check3);
        input5check4=findViewById(R.id.input5check4);
        input5check5=findViewById(R.id.input5check5);
        input5check6=findViewById(R.id.input5check6);
        input5check7=findViewById(R.id.input5check7);
        input6check1=findViewById(R.id.input6check1);
        input6check2=findViewById(R.id.input6check2);
        input6check3=findViewById(R.id.input6check3);
        input6check4=findViewById(R.id.input6check4);
        input7check1=findViewById(R.id.input7check1);
        input7check2=findViewById(R.id.input7check2);
        input7check3=findViewById(R.id.input7check3);
        input7check4=findViewById(R.id.input7check4);
        input7check5=findViewById(R.id.input7check5);
        input7check6=findViewById(R.id.input7check6);
        input7check7=findViewById(R.id.input7check7);
        input7check8=findViewById(R.id.input7check8);
        input8check1=findViewById(R.id.input8check1);
        input8check2=findViewById(R.id.input8check2);
        input8check3=findViewById(R.id.input8check3);
        input8check4=findViewById(R.id.input8check4);

        layout2.setVisibility(View.GONE);
        layout3.setVisibility(View.GONE);
        layout4.setVisibility(View.GONE);
        layout5.setVisibility(View.GONE);
        layout6.setVisibility(View.GONE);
        layout7.setVisibility(View.GONE);
        layout8.setVisibility(View.GONE);

        submit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(input1.getText().toString().isEmpty())
                {
                    input1.setError("Empty");

                }
                else
                {
                    age=Integer.parseInt(input1.getText().toString());
                    input1.setEnabled(false);
                    layout2.setVisibility(View.VISIBLE);
                    submit1.setOnClickListener(null);
                    if(age<=20)
                        risk=risk-3;
                    else if(age<=35)
                        risk=risk-1;
                    else if(risk<=45)
                        risk=risk+1;
                    else
                        risk=risk+3;

                    layout2.getParent().requestChildFocus(layout2,layout2);
                }

            }
        });

        submit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(input2check1.isChecked()||input2check2.isChecked()||input2check3.isChecked()){
                    input2check1.setEnabled(false);
                    input2check2.setEnabled(false);
                    input2check3.setEnabled(false);
                    submit2.setOnClickListener(null);
                    layout3.setVisibility(View.VISIBLE);
                    submit2.setError(null);
                    layout3.getParent().requestChildFocus(layout3,layout3);


                }
                else
                {
                    submit2.setError("select one");
                }
            }
        });

        submit3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(input3check1.isChecked()||input3check2.isChecked()||input3check3.isChecked()||input3check4.isChecked())
                {
                    input3check4.setEnabled(false);
                    input3check3.setEnabled(false);
                    input3check2.setEnabled(false);
                    input3check1.setEnabled(false);
                    submit3.setOnClickListener(null);
                    layout4.setVisibility(View.VISIBLE);
                    submit3.setError(null);
                    if(input3check4.isChecked())
                        risk=risk+1;
                    if(input3check3.isChecked())
                        risk=risk+2;
                    if(input3check2.isChecked())
                        risk=risk+1;
                    if(input3check1.isChecked())
                        risk=risk-2;

                    layout4.getParent().requestChildFocus(layout4,layout4);

                }
                else
                {
                    submit3.setError("select one");
                }
            }
        });

        submit4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(input4check1.isChecked()||input4check2.isChecked()||input4check3.isChecked()||input4check4.isChecked()
                        ||input4check5.isChecked()||input4check6.isChecked())
                {
                    input4check6.setEnabled(false);
                    input4check5.setEnabled(false);
                    input4check4.setEnabled(false);
                    input4check3.setEnabled(false);
                    input4check2.setEnabled(false);
                    input4check1.setEnabled(false);
                    submit4.setOnClickListener(null);
                    layout5.setVisibility(View.VISIBLE);
                    layout5.getParent().requestChildFocus(layout5,layout5);
                    submit4.setError(null);
                    if(input4check1.isChecked())
                        risk=risk+3;
                    if(input4check2.isChecked())
                        risk=risk+1;
                    if(input4check3.isChecked())
                        risk=risk+3;
                    if(input4check4.isChecked())
                        risk=risk+2;
                    if(input4check5.isChecked())
                        risk=risk+1;
                    if(input4check6.isChecked())
                        risk=risk-2;

                }
                else
                {
                    submit4.setError("select one");
                }
            }
        });

        submit5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(input5check1.isChecked()||input5check2.isChecked()||input5check3.isChecked()||input5check4.isChecked()||
                input5check5.isChecked()||input5check6.isChecked()||input5check7.isChecked())
                {
                    input5check7.setEnabled(false);
                    input5check6.setEnabled(false);
                    input5check5.setEnabled(false);
                    input5check4.setEnabled(false);
                    input5check3.setEnabled(false);
                    input5check2.setEnabled(false);
                    input5check1.setEnabled(false);
                    submit5.setOnClickListener(null);
                    layout6.setVisibility(View.VISIBLE);
                    layout6.getParent().requestChildFocus(layout6,layout6);
                    submit5.setError(null);
                    if(input5check1.isChecked())
                        risk=risk+3;
                    if(input5check2.isChecked())
                        risk=risk+4;
                    if(input5check3.isChecked())
                        risk=risk+3;
                    if(input5check4.isChecked())
                        risk=risk+1;
                    if(input5check5.isChecked())
                        risk=risk+2;
                    if(input5check6.isChecked())
                        risk=risk+1;
                    if(input5check7.isChecked())
                        risk=risk-2;



                }
                else
                {
                    submit5.setError("select one");
                }
            }
        });

        submit6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(input6check1.isChecked()||input6check2.isChecked()||input6check3.isChecked()||input6check4.isChecked())
                {
                    input6check4.setEnabled(false);
                    input6check3.setEnabled(false);
                    input6check2.setEnabled(false);
                    input6check1.setEnabled(false);
                    submit6.setOnClickListener(null);
                    layout7.setVisibility(View.VISIBLE);
                    layout7.getParent().requestChildFocus(layout7,layout7);

                    submit6.setError(null);
                    if(input6check1.isChecked())
                        risk=risk-3;
                    if(input6check2.isChecked())
                        risk=risk-1;
                    if(input6check3.isChecked())
                        risk=risk+3;
                    if(input6check4.isChecked())
                        risk=risk+5;


                }
                else
                {
                    submit6.setError("select one");
                }
            }
        });

        submit7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(input7check1.isChecked()||input7check2.isChecked()||input7check3.isChecked()||input7check4.isChecked()||
                input7check5.isChecked()||input7check6.isChecked()||input7check7.isChecked()||input7check8.isChecked())
                {
                    input7check8.setEnabled(false);
                    input7check7.setEnabled(false);
                    input7check6.setEnabled(false);
                    input7check5.setEnabled(false);
                    input7check4.setEnabled(false);
                    input7check3.setEnabled(false);
                    input7check2.setEnabled(false);
                    input7check1.setEnabled(false);
                    submit7.setOnClickListener(null);
                    layout8.setVisibility(View.VISIBLE);
                    layout8.getParent().requestChildFocus(layout8,layout8);
                    submit7.setError(null);
                    if(input7check1.isChecked())
                        risk=risk+1;
                    if(input7check2.isChecked())
                        risk=risk+1;
                    if(input7check3.isChecked())
                        risk=risk+2;
                    if(input7check4.isChecked())
                        risk=risk+1;
                    if(input7check5.isChecked())
                        risk=risk+3;
                    if(input7check6.isChecked())
                        risk=risk+3;
                    if(input7check7.isChecked())
                        risk=risk+2;
                    if(input7check8.isChecked())
                        risk=risk-2;


                }
                else
                {
                    submit7.setError("select one");
                }
            }
        });
        submit8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(input8check1.isChecked()||input8check2.isChecked()||input8check3.isChecked()||input8check4.isChecked())
                {
                    input8check4.setEnabled(false);
                    input8check3.setEnabled(false);
                    input8check2.setEnabled(false);
                    input8check1.setEnabled(false);
                    submit8.setError(null);

                    if(input8check1.isChecked())
                        risk=risk+0;
                    if(input8check2.isChecked())
                        risk=risk-1;
                    if(input8check3.isChecked())
                        risk=risk+2;
                    if(input8check1.isChecked())
                        risk=risk+3;

                    Double res= (risk/41);

                    Intent intent=new Intent(MainActivity.this,Main2Activity.class);
                    intent.putExtra("risk",String.valueOf(res));
                    startActivity(intent);
                }
                else
                {
                    submit8.setError("select one");
                }
            }
        });
    }
}
