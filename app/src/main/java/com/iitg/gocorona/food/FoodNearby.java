package com.iitg.gocorona.food;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.iitg.gocorona.R;

import java.util.HashMap;

public class FoodNearby extends Fragment {

    public FoodNearby() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_food_your, container, false);
    }


    RecyclerView foodreports;
    DatabaseReference allfooddatabaseReference;
    FirebaseAuth mAuth;
    SwipeRefreshLayout swipe;


    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<foodusers, AllFoodViewholder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<foodusers, AllFoodViewholder>(
                foodusers.class, R.layout.layout_food_query, AllFoodViewholder.class, allfooddatabaseReference
        ) {
            @Override
            protected void populateViewHolder(AllFoodViewholder viewHolder, final foodusers model, final int position) {
                viewHolder.setFoodQuery(model.getFoodQuery());
                viewHolder.setContact(model.getContact());
                viewHolder.setLocation(model.getLocation());
                final String posid = getRef(position).getKey();
                viewHolder.mview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "+91" + model.getContact(), null));
                        startActivity(i);
                    }
                });
            }
        };
        foodreports.setAdapter(firebaseRecyclerAdapter);

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

        public void setContact(final String contact) {
            TextView contactquery = mview.findViewById(R.id.contact);
            contactquery.setText(contact);

            Button contactbtn = mview.findViewById(R.id.contactbtn);
            contactbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callThis(contact);
                }
            });
        }

        private void callThis(String contact) {
            Intent i = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "+91" + contact, null));
            mview.getContext().startActivity(i);
        }

        public void setLocation(final String location) {
            TextView locationquery = mview.findViewById(R.id.location);
            locationquery.setText(location);
            Button locationbtn = (Button) mview.findViewById(R.id.locationbtn);
            locationbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendToGoogleMap(location);
                }
            });
        }

        private void sendToGoogleMap(String location) {

        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        swipe = view.findViewById(R.id.swipe);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                FirebaseRecyclerAdapter<foodusers, AllFoodViewholder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<foodusers, AllFoodViewholder>(
                        foodusers.class, R.layout.layout_food_query, AllFoodViewholder.class, allfooddatabaseReference
                ) {
                    @Override
                    protected void populateViewHolder(AllFoodViewholder viewHolder, final foodusers model, final int position) {
                        viewHolder.setFoodQuery(model.getFoodQuery());
                        viewHolder.setContact(model.getContact());
                        viewHolder.setLocation(model.getLocation());
                        final String posid = getRef(position).getKey();
                        viewHolder.mview.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "+91" + model.getContact(), null));
                                startActivity(i);
                            }
                        });
                    }
                };
                foodreports.setAdapter(firebaseRecyclerAdapter);
                swipe.setRefreshing(false);
            }
        });
        foodreports = view.findViewById(R.id.recycler_view);

        foodreports.setClickable(true);
        foodreports.setHasFixedSize(true);
        foodreports.setLayoutManager(new LinearLayoutManager(getContext()));


        allfooddatabaseReference = FirebaseDatabase.getInstance().getReference().child("Reports").child("food");
        allfooddatabaseReference.keepSynced(true);
        
    }

    private void showDialog(Context context) {
        final Dialog dialog = new Dialog(context);
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
    }

    private void addtodatabase(String food, String contact, String location) {
        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = current_user.getUid();
        String device_token = FirebaseInstanceId.getInstance().getToken();
        DatabaseReference newfoodquery = FirebaseDatabase.getInstance().getReference().child("Reports").child("food").child(device_token);
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
}