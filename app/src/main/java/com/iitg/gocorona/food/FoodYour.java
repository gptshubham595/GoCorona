package com.iitg.gocorona.food;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import com.iitg.gocorona.R;

import java.util.HashMap;

public class FoodYour extends Fragment {

    public FoodYour() {
    }


    Button add, add2;
    long i=0;
    RecyclerView foodreports;
    DatabaseReference allfooddatabaseReference;
    FirebaseAuth mAuth;
    SwipeRefreshLayout swipe;


    @Override
    public void onStart() {
        super.onStart();
        try {
            add.setVisibility(View.INVISIBLE);
            add2.setVisibility(View.VISIBLE);
            FirebaseRecyclerAdapter<foodUsersOwn, AllFoodViewholder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<foodUsersOwn, AllFoodViewholder>(
                    foodUsersOwn.class, R.layout.layout_food_query, AllFoodViewholder.class, allfooddatabaseReference
            ) {
                @Override
                protected void populateViewHolder(AllFoodViewholder viewHolder, final foodUsersOwn model, final int position) {


                    viewHolder.setFoodQuery(model.getFoodQuery());

                    final String posid = getRef(position).getKey();
                    viewHolder.mview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showDialog2(getContext(), posid);

                        }
                    });
                }
            };
            foodreports.setAdapter(firebaseRecyclerAdapter);
        } catch (Exception e) {
            add.setVisibility(View.VISIBLE);
            add2.setVisibility(View.INVISIBLE);
            e.printStackTrace();
            Toast.makeText(getContext(), "ERROR..", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDialog2(final Context context, final String pos) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setTitle("Edit");
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_option_food_choice);
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
                i=geti();
                DatabaseReference newfoodquery = FirebaseDatabase.getInstance().getReference().child("Reports").child("food").child(device_token).child(pos);
                newfoodquery.keepSynced(true);
                newfoodquery.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Unknown Error.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                dialog.dismiss();

            }
        });
        dialog.show();
    }

    public static class AllFoodViewholder extends RecyclerView.ViewHolder {
        View mview;

        public AllFoodViewholder(@NonNull View itemView) {
            super(itemView);
            mview = itemView;
        }

        public void setFoodQuery(String foodQuery) {
            TextView foodquery = mview.findViewById(R.id.foodQuery);
            foodquery.setText(foodQuery);

        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_food_your, container, false);

        mAuth = FirebaseAuth.getInstance();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                i=geti();
            }
        },500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                i=geti();
            }
        },1500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                i=geti();
            }
        },2500);

        swipe = view.findViewById(R.id.swipe);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                FirebaseRecyclerAdapter<foodUsersOwn, AllFoodViewholder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<foodUsersOwn, AllFoodViewholder>(
                        foodUsersOwn.class, R.layout.layout_food_query_own, AllFoodViewholder.class, allfooddatabaseReference
                ) {
                    @Override
                    protected void populateViewHolder(AllFoodViewholder viewHolder, final foodUsersOwn model, final int position) {
                        viewHolder.setFoodQuery(model.getFoodQuery());
                        final String posid = getRef(position).getKey();
                        viewHolder.mview.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showDialog2(getContext(),posid);
                            }
                        });
                    }
                };
                foodreports.setAdapter(firebaseRecyclerAdapter);
                swipe.setRefreshing(false);
            }
        });

        add = view.findViewById(R.id.add);
        foodreports = view.findViewById(R.id.recycler);

        add2 = view.findViewById(R.id.add2);


        foodreports.setHasFixedSize(true);
        foodreports.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = current_user.getUid();
        String device_token = FirebaseInstanceId.getInstance().getToken();

        allfooddatabaseReference = FirebaseDatabase.getInstance().getReference().child("Reports").child("food").child(device_token);
        allfooddatabaseReference.keepSynced(true);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=geti();
                i++;
                showDialog(getContext());
            }
        });

        add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=geti();
                i++;showDialog(getContext());
            }
        });
        return view;
    }

    private void showDialog(Context context) {
        Toast.makeText(context, "Shohwing", Toast.LENGTH_SHORT).show();
        final Dialog dialog = new Dialog(getActivity());
        dialog.setTitle("ADD YOUR FOOD CRISIS");
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_option_food);
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
                    food.setError("Write about food shortage!");
                if (TextUtils.isEmpty(contact.getText().toString().trim()))
                    food.setError("Write your contact!");
                if (TextUtils.isEmpty(location.getText().toString().trim()))
                    food.setError("Write your location!");

                if (!TextUtils.isEmpty(food.getText().toString().trim()) && !TextUtils.isEmpty(contact.getText().toString().trim()) && !TextUtils.isEmpty(location.getText().toString().trim())) {
                    addtodatabase(food.getText().toString().trim(), contact.getText().toString().trim(), location.getText().toString().trim());
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    private void addtodatabase(final String food, final String contact, final String location) {
        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = current_user.getUid();
        final String device_token = FirebaseInstanceId.getInstance().getToken();
        i=geti();
        i++;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DatabaseReference newfoodquery = FirebaseDatabase.getInstance().getReference().child("Reports").child("food").child(device_token).child(String.valueOf(i));
                newfoodquery.keepSynced(true);
                HashMap<String, String> userMap = new HashMap<>();
                userMap.put("foodQuery", food);
                userMap.put("contact", contact);
                userMap.put("location", location);

                newfoodquery.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Posted!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Unknown Error.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        },100);

    }
    private long geti() {
        final long[] i = {0};
        String device_token = FirebaseInstanceId.getInstance().getToken();
        DatabaseReference mi = FirebaseDatabase.getInstance().getReference().child("Reports").child("food").child(device_token);
        mi.keepSynced(true);
        mi.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if(!dataSnapshot.exists()){}
                else{
                    i[0] =dataSnapshot.getChildrenCount();
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