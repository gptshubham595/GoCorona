package com.iitg.gocorona.query;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iitg.gocorona.R;

import java.util.ArrayList;

public class reportQuery extends AppCompatActivity {


    private RecyclerView recyclerView;
    private find_suspect_adapter adapter;
    private FloatingActionButton fb;
    private ArrayList<find_suspect_class> arrayList;
    private final String DEFAULT_STRING = "NULL";
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_query);

        recyclerView = findViewById(R.id.findSuspectRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList = new ArrayList<>();
        adapter = new find_suspect_adapter(this,arrayList);
        recyclerView.setAdapter(adapter);

        databaseReference.child("query").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    arrayList.add(new find_suspect_class(snapshot.child("name").getValue().toString(),
                            snapshot.child("image").getValue().toString(),
                            snapshot.child("post").getValue().toString(),
                            snapshot.getKey().toString(),
                            snapshot.child("text").getValue().toString(),
                            Integer.parseInt(snapshot.child("reported").getValue().toString())));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
