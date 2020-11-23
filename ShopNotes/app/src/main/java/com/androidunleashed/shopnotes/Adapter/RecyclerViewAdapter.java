package com.androidunleashed.shopnotes.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.androidunleashed.shopnotes.Data.DataBaseHandler;
import com.androidunleashed.shopnotes.ListActivity;
import com.androidunleashed.shopnotes.MainActivity;
import com.androidunleashed.shopnotes.Model.Item;
import com.androidunleashed.shopnotes.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;


import java.text.MessageFormat;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;
    List<Item> itemList;

    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private LayoutInflater inflater;

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
        holder.qty.setText(MessageFormat.format("Nos.{0} ", item.getItemQty()));
        holder.date.setText(MessageFormat.format("Added On: {0} ", item.getDate()));

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

            int pos = getAdapterPosition();
            Item item = itemList.get(pos);

            switch (v.getId()) {
                case R.id.editButton:

                    editItem(item);


                    break;

                case R.id.deleteButton:


                    deleteItem(item.getId());

                    break;
            }
        }

        private void editItem(final Item item) {

            Button SaveButton;
            final TextInputEditText ItemName, ItemQty;
            TextView textView;


            builder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);

            View view = inflater.inflate(R.layout.popup,null);

            textView = view.findViewById(R.id.titleText);
            ItemName = view.findViewById(R.id.itemName);
            ItemQty = view.findViewById(R.id.itemQuantity);

            SaveButton = view.findViewById(R.id.saveButton);

            SaveButton.setText("Update");
            textView.setText("Edit Item");
            ItemName.setText(item.getItemName());
            ItemQty.setText(String.valueOf(item.getItemQty()));


            builder.setView(view);
            dialog = builder.create(); //creating dialogue object
            dialog.show();

            SaveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DataBaseHandler db = new DataBaseHandler(context);


                    String name = ItemName.getText().toString();
                    String qty = ItemQty.getText().toString();


                    if( !name.isEmpty() && !qty.isEmpty() ){

                        item.setItemName(name);
                        item.setItemQty(Integer.valueOf(qty));

                        db.updateItem(item);
                        notifyItemChanged(getAdapterPosition(),item);

                        Toast.makeText(context,"Updated!",Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(context,"Empty Field not Alllowed!",Toast.LENGTH_SHORT).show();
                    }

                    dialog.dismiss();

                }
            });



        }

        private void deleteItem(final int id) {

            builder = new AlertDialog.Builder(new ContextThemeWrapper(context,R.style.AlertDialogCustom));

            builder.setMessage("Are you sure, You wanted to delete this item?");


            builder.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            DataBaseHandler db = new DataBaseHandler(context);
                            db.deleteItem(id);

                            itemList.remove(getAdapterPosition());
                            notifyItemRemoved(getAdapterPosition());

//                            if (db.getItemsCount() > 0) {
//                                Intent intent = new Intent(context,ListActivity.class);
//                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                context.startActivity(intent);
//
//                            }

                            Toast.makeText(context,"Deleted!",Toast.LENGTH_SHORT).show();

                            dialog.dismiss();

                        }
                    });

            builder.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            dialog = builder.create();
            dialog.show();

        }
    }
}
