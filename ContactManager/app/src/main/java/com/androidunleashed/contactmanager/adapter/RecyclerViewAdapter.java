package com.androidunleashed.contactmanager.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidunleashed.contactmanager.FullDetails;
import com.androidunleashed.contactmanager.MainActivity;
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView cname,cphn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            cname = itemView.findViewById(R.id.cname);
            cphn = itemView.findViewById(R.id.cnum);


        }

        @Override
        public void onClick(View v) {

            int p = getAdapterPosition();

            Contacts contacts = contactsList.get(p);

            Intent intent = new Intent(context, FullDetails.class);
            intent.putExtra("cname",contacts.getName());
            intent.putExtra("phn",contacts.getPhoneNumber());
            context.startActivity(intent);

            Log.d("name", "onClick: "+contacts.getName());

        }
    }
}
