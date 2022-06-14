package com.example.talkappandroid.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.talkappandroid.model.ContactItem;
import com.example.talkappandroid.R;

import java.util.List;

public class ContactsListAdapter extends RecyclerView.Adapter<ContactsListAdapter.ContactViewHolder> {

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvLastMessage;
        private TextView tvTime;
        private ImageView ivPic;


        private ContactViewHolder(View itemView){
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvLastMessage = itemView.findViewById(R.id.tvLastMessage);
            tvTime = itemView.findViewById(R.id.tvTime);
            ivPic = itemView.findViewById(R.id.ivPic);
        }
    }

    public interface OnContactClicked {
        void onContactClicked(int position);
    }

    private OnContactClicked listener;
    private List<ContactItem> contactItems;

    public void setListener(OnContactClicked listener) {
        this.listener = listener;
    }

    public void setContactItems(List<ContactItem> items) {
        contactItems = items;
    }

    public ContactsListAdapter(List<ContactItem> contactItems) {
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
            holder.itemView.setOnClickListener(v -> listener.onContactClicked(position));
        }

    }

    @Override
    public int getItemCount() {
        return 0;
    }

}
