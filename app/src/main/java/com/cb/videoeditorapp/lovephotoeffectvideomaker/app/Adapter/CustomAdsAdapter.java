package com.cb.videoeditorapp.lovephotoeffectvideomaker.app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.R;
import com.cb.videoeditorapp.lovephotoeffectvideomaker.app.model.LlpAdApp;
import com.bumptech.glide.Glide;


import java.util.List;

public class CustomAdsAdapter extends RecyclerView.Adapter<CustomAdsAdapter.ContactHolder> {

    String packgename;
    // List to store all the contact details
    private List<LlpAdApp> contactsList;
    private Context mContext;

    // Counstructor for the Class
    public CustomAdsAdapter(List<LlpAdApp> contactsList, Context context) {
        this.contactsList = contactsList;
        this.mContext = context;
    }

    // This method creates views for the RecyclerView by inflating the layout
    // Into the viewHolders which helps to display the items in the RecyclerView
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // Inflate the layout view you have created for the list rows here
        View view = layoutInflater.inflate(R.layout.item_ads, parent, false);
        return new ContactHolder(view);
    }

    @Override
    public int getItemCount() {
        return contactsList == null? 0: contactsList.size();
    }

    // This method is called when binding the data to the views being created in RecyclerView
    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, final int position) {
        final LlpAdApp contact = contactsList.get(position);


        packgename=contact.getAppurl().substring(contact.getAppurl().indexOf("=")+1  , contact.getAppurl().length());

        holder.appName.setSelected(true);

        Log.d("adapter", packgename);
        // Set the data to the views here
        holder.setContactName(contact.getAppname());

        Glide.with(holder.itemView.getContext()).load(contact.getAppicon()).into(holder.appIcon);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(contact.getAppurl()); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
              //  apiCall();
            }
        });

        holder.appUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Uri uri = Uri.parse(contact.getAppurl()); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
               // apiCall();
            }
        });



        // You can set click listners to indvidual items in the viewholder here
        // make sure you pass down the listner or make the Data members of the viewHolder public

    }

    // This is your ViewHolder class that helps to populate data to the view
    public class ContactHolder extends RecyclerView.ViewHolder {

        private TextView appName;
        private ImageView appIcon;
        private Button appUrl;

        public ContactHolder(View itemView) {
            super(itemView);

            appName = itemView.findViewById(R.id.Tvname);
            appIcon = itemView.findViewById(R.id.Ivicon);
            appUrl = itemView.findViewById(R.id.btninstall);

        }

        public void setContactName(String name) {
            appName.setText(name);
        }


    }




}