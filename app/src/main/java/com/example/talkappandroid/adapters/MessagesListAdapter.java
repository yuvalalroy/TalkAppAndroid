package com.example.talkappandroid.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.talkappandroid.R;
import com.example.talkappandroid.model.ContactItem;
import com.example.talkappandroid.model.MessageItem;

import java.util.List;

public class MessagesListAdapter extends RecyclerView.Adapter<MessagesListAdapter.MessageViewHolder> {

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        private TextView tvContent;
        private TextView tvDate;
        private boolean sent;


        private MessageViewHolder(View itemView){
            super(itemView);
            tvContent = itemView.findViewById(R.id.msg_input);
            tvDate = itemView.findViewById(R.id.tvLastMessage);
        }
    }

    private List<MessageItem> messageItems;

    public void setMessageItems(List<MessageItem> items) {
        messageItems = items;
    }

    public MessagesListAdapter(List<MessageItem> messageItems) {
        this.messageItems = messageItems;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_message_item,
                parent, false);
        return new MessageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        if(messageItems != null){
            MessageItem current = messageItems.get(position);
            holder.tvContent.setText(current.getContent());
            holder.tvDate.setText(current.getCreated());
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

}
