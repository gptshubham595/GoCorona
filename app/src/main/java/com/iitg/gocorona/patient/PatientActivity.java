package com.iitg.gocorona.patient;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.iitg.gocorona.MainActivity;
import com.iitg.gocorona.R;

import java.util.HashMap;

public class PatientActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private FirebaseAuth mAuth;
    long i = 0;
    Button add, add2;
    RecyclerView patientreports;
    DatabaseReference allpatientdatabaseReference;
    SwipeRefreshLayout swipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        mAuth = FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                i = geti();
            }
        }, 500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                i = geti();
            }
        }, 1500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                i = geti();
            }
        }, 2500);

        mToolbar = findViewById(R.id.login_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("PATIENT REPPORTED");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                Intent i = new Intent(PatientActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        patientreports = findViewById(R.id.recycler_view);
        add = findViewById(R.id.add);
        add2 = findViewById(R.id.add2);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = geti();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showDialog(getApplicationContext(), String.valueOf(i));
                    }
                }, 100);
            }
        });

        add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = geti();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showDialog(getApplicationContext(), String.valueOf(i));
                    }
                }, 100);

            }
        });

        patientreports.setClickable(true);
        patientreports.setHasFixedSize(true);
        patientreports.setLayoutManager(new LinearLayoutManager(this));

        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = current_user.getUid();
        String device_token = FirebaseInstanceId.getInstance().getToken();
        allpatientdatabaseReference = FirebaseDatabase.getInstance().getReference().child("Reports").child("patient").child(device_token);
        allpatientdatabaseReference.keepSynced(true);


    }

    private void showDialog(Context context, final String pos) {
        final Dialog dialog = new Dialog(context);
        dialog.setTitle("ADD YOUR FOOD CRISIS");
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_option_patient);
        Button ok = dialog.findViewById(R.id.okDIALOG);
        Button cancel = dialog.findViewById(R.id.cancelDIALOG);
        final TextView food, contact, location;
        food = dialog.findViewById(R.id.foodqueryDIALOG);
        contact = dialog.findViewById(R.id.contactnumberDIALOG);
        location = dialog.findViewById(R.id.locationqueryDIALOG);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(food.getText().toString().trim()))
                    food.setError("Write about person!");
                if (TextUtils.isEmpty(contact.getText().toString().trim()))
                    food.setError("Write his contact!");
                if (TextUtils.isEmpty(location.getText().toString().trim()))
                    food.setError("Write his Last known location!");

                if (!TextUtils.isEmpty(food.getText().toString().trim()) && !TextUtils.isEmpty(contact.getText().toString().trim()) && !TextUtils.isEmpty(location.getText().toString().trim())) {
                    addtodatabase(food.getText().toString().trim(), contact.getText().toString().trim(), location.getText().toString().trim(), pos);
                    dialog.dismiss();
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            add.setVisibility(View.INVISIBLE);
            add2.setVisibility(View.VISIBLE);

            FirebaseRecyclerAdapter<reportedUsers, AllPatientViewholder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<reportedUsers, AllPatientViewholder>(
                    reportedUsers.class, R.layout.layout_food_reported_own, AllPatientViewholder.class, allpatientdatabaseReference
            ) {
                @Override
                protected void populateViewHolder(AllPatientViewholder viewHolder, final reportedUsers model, final int position) {
                    viewHolder.setReported(model.getReported());

                    final String posid = getRef(position).getKey();
                    viewHolder.mview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showDialog2(getApplicationContext(), posid);
                        }
                    });
                }
            };
            patientreports.setAdapter(firebaseRecyclerAdapter);
        } catch (Exception e) {
            e.printStackTrace();
            add.setVisibility(View.VISIBLE);
            add2.setVisibility(View.INVISIBLE);
            Toast.makeText(PatientActivity.this, "ERROR..", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDialog2(Context context, final String pos) {
        final Dialog dialog = new Dialog(context);
        dialog.setTitle("Edit");
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_option_patient_choice);
        Button delete = dialog.findViewById(R.id.deleteDIALOG);
        Button leave = dialog.findViewById(R.id.leaveDIALOG);

        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = current_user.getUid();
                String device_token = FirebaseInstanceId.getInstance().getToken();
                DatabaseReference newfoodquery = FirebaseDatabase.getInstance().getReference().child("Reports").child("patient").child(device_token).child(String.valueOf(pos));
                newfoodquery.keepSynced(true);
                newfoodquery.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(PatientActivity.this, "Done", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(PatientActivity.this, "Unknown Error.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                dialog.dismiss();

            }
        });
    }

    public static class AllPatientViewholder extends RecyclerView.ViewHolder {
        View mview;

        public AllPatientViewholder(@NonNull View itemView) {
            super(itemView);
            mview = itemView;
        }

        public void setReported(String foodQuery) {
            TextView reported = mview.findViewById(R.id.reportedQuery);
            reported.setText(foodQuery);

        }

    }

    private void addtodatabase(final String food, final String contact, final String location, String pos) {
        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = current_user.getUid();
        final String device_token = FirebaseInstanceId.getInstance().getToken();
        i = geti();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DatabaseReference newfoodquery = FirebaseDatabase.getInstance().getReference().child("Reports").child("patient").child(device_token).child(String.valueOf(i));
                newfoodquery.keepSynced(true);
                HashMap<String, String> userMap = new HashMap<>();
                userMap.put("Reported", food);
                userMap.put("contact", contact);
                userMap.put("location", location);

                newfoodquery.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Posted!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Unknown Error.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        }, 100);

    }

    private long geti() {
        final long[] i = {0};
        String device_token = FirebaseInstanceId.getInstance().getToken();
        DatabaseReference mi = FirebaseDatabase.getInstance().getReference().child("Reports").child("patient").child(device_token);
        mi.keepSynced(true);
        mi.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (!dataSnapshot.exists()) {
                } else {
                    i[0] = dataSnapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
        return i[0];
    }
}
