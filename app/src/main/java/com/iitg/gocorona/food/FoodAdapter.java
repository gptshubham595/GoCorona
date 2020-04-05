package com.iitg.gocorona.food;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iitg.gocorona.R;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    private Context context;
    private ArrayList<foodusers> itemList;

    public FoodAdapter(Context context, ArrayList<foodusers> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_food_query, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position)
    {
        final foodusers model = itemList.get(position);
        viewHolder.setFoodQuery(model.getFoodQuery());
        viewHolder.setContact(model.getContact());
        viewHolder.setLocation(model.getLocation());
//        viewHolder.mview.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "+91" + model.getContact(), null));
//                startActivity(i);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View mview;

        public ViewHolder(@NonNull View itemView) {
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
}

