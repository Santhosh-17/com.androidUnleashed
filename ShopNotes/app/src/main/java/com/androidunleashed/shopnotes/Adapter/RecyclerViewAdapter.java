package com.androidunleashed.shopnotes.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidunleashed.shopnotes.Model.Item;
import com.androidunleashed.shopnotes.R;


import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;
    List<Item> itemList;

    public RecyclerViewAdapter(Context context, List<Item> itemList) {

        this.context = context;
        this.itemList = itemList;

    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row,parent,false);

        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {

        Item item = itemList.get(position);

        holder.name.setText(item.getItemName()+" ");
        holder.qty.setText(item.getItemQty()+" ");
        holder.date.setText(item.getDate()+" ");

    }

    @Override
    public int getItemCount() {

        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView name, qty, date;
        public Button editButton , delButton;
        public int id;

        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);

            context = ctx;

            name = itemView.findViewById(R.id.iName);
            qty = itemView.findViewById(R.id.iQty);
            date = itemView.findViewById(R.id.iDate);

            editButton = itemView.findViewById(R.id.editButton);
            delButton = itemView.findViewById(R.id.deleteButton);

            editButton.setOnClickListener(this);
            delButton.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {

         switch (v.getId()){
             case R.id.editButton:

                 break;

             case R.id.deleteButton:

                 break;

         }
        }
    }
}
