package com.example.talkappandroid.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.talkappandroid.TalkAppApplication;
import com.example.talkappandroid.model.ContactItem;
import com.example.talkappandroid.R;

import java.util.List;

public class ContactsListAdapter extends RecyclerView.Adapter<ContactsListAdapter.ContactViewHolder> {



    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvLastMessage, tvTime;
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
        notifyDataSetChanged();
    }

    public void setContactItems(List<ContactItem> items) {
        contactItems = items;
        notifyDataSetChanged();
    }

    public ContactsListAdapter(List<ContactItem> contactItems) {
        System.out.println(contactItems);
        this.contactItems = contactItems;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_contacts_item,
                parent, false);
        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        if(contactItems != null){
            ContactItem current = contactItems.get(position);
            holder.tvName.setText(current.getName());
            holder.tvLastMessage.setText(current.getLast());
            holder.tvTime.setText(current.getLastdate());
            holder.ivPic.setImageResource(R.drawable.ic_avatar);
            holder.itemView.setOnClickListener(v -> listener.onContactClicked(position));
        }
    }

    public void clear() {
        contactItems.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return contactItems.size();
    }

    public List<ContactItem> getContactItems() { return this.contactItems; }

}
