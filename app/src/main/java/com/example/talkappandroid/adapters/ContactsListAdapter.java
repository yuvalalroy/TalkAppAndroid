package com.example.talkappandroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.talkappandroid.ContactItem;
import com.example.talkappandroid.R;

import java.util.List;

public class ContactsListAdapter extends RecyclerView.Adapter<ContactsListAdapter.ContactViewHolder> {

    class ContactViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvName;
        private final TextView tvLastMessage;
        private final TextView tvTime;
        private final ImageView ivPic;

        private ContactViewHolder(View itemView){
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvLastMessage = itemView.findViewById(R.id.tvLastMessage);
            tvTime = itemView.findViewById(R.id.tvTime);
            ivPic = itemView.findViewById(R.id.ivPic);
        }
    }

    private final LayoutInflater mInflater;
    private List<ContactItem> contactItems;

    public void setContactItems(List<ContactItem> items) {
        contactItems = items;
    }

    public ContactsListAdapter(Context context, List<ContactItem> contactItems) {
        mInflater = LayoutInflater.from(context);
        this.contactItems = contactItems;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /*View itemView = mInflater.inflate(R.layout.activity_contacts_item, parent, false);*/
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_contacts_item,
                parent, false);
        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        if(contactItems != null){
            ContactItem current = contactItems.get(position);
            holder.tvName.setText(current.getName());
            holder.tvLastMessage.setText(current.getLastMessage());
            holder.tvTime.setText(current.getLastDate());
            holder.ivPic.setImageResource(current.getAccountPic());
        }

    }


    @Override
    public int getItemCount() {
        if(contactItems != null)
            return contactItems.size();
        return 0;
    }

    public List<ContactItem> getContactItems(){ return contactItems;}

}
