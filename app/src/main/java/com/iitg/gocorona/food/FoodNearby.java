package com.iitg.gocorona.food;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.iitg.gocorona.R;

public class FoodNearby extends Fragment {

    public FoodNearby() {
    }



    RecyclerView foodreports;
    Query allfooddatabaseReference;
    FirebaseAuth mAuth;
    SwipeRefreshLayout swipe;


    @Override
    public void onStart() {
        super.onStart();
        try {
            FirebaseRecyclerAdapter<foodusers, AllFoodViewholder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<foodusers, AllFoodViewholder>(
                    foodusers.class, R.layout.layout_food_query, AllFoodViewholder.class, allfooddatabaseReference
            ) {
                @Override
                protected void populateViewHolder(final AllFoodViewholder viewHolder, final foodusers model, final int position) {
                    FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

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

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }
            };
            foodreports.setAdapter(firebaseRecyclerAdapter);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "ERROR..", Toast.LENGTH_SHORT).show();
        }
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
            Button locationbtn = mview.findViewById(R.id.locationbtn);
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_food_your, container, false);

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
        foodreports = view.findViewById(R.id.recycler);


        foodreports.setHasFixedSize(true);
        foodreports.setLayoutManager(new LinearLayoutManager(getContext()));


        allfooddatabaseReference = FirebaseDatabase.getInstance().getReference().child("Reports").child("food");
        allfooddatabaseReference.keepSynced(true);
        return view;
    }



}