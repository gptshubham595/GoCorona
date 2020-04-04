package com.iitg.gocorona.medicalchatbot;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.iitg.gocorona.R;

import java.util.ArrayList;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private ArrayList<String> myDataset;
    private Context cont;
    Button more;

    public MyAdapter(ArrayList<String> myDataset) {
        this.myDataset = myDataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_chatbot, parent, false);
        ViewHolder vh = new ViewHolder(v);
        more = (Button) v.findViewById(R.id.more);
        cont = v.getContext();
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.myTitle.setText(myDataset.get(position));

        String google = "https://www.google.com.eg/search?q=";
        String cond = myDataset.get(position);
        final String search = google.concat(cond);

        more.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(search));
                cont.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView myTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            myTitle = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
