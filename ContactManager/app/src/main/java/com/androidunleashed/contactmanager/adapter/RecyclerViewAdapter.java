package com.androidunleashed.contactmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidunleashed.contactmanager.R;
import com.androidunleashed.contactmanager.model.Contacts;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Contacts> contactsList;

    public RecyclerViewAdapter(Context context, List<Contacts> contactsList) {
        this.context = context;
        this.contactsList = contactsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Contacts contacts = contactsList.get(position);

        holder.cname.setText(contacts.getName());
        holder.cphn.setText(contacts.getPhoneNumber());


    }


    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView cname,cphn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cname = itemView.findViewById(R.id.cname);
            cphn = itemView.findViewById(R.id.cnum);


        }
    }
}