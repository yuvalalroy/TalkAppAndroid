package com.example.talkappandroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.talkappandroid.R;
import com.example.talkappandroid.api.MessageServiceAPI;
import com.example.talkappandroid.model.ContactItem;
import com.example.talkappandroid.model.MessageItem;

import java.util.List;

public class MessagesListAdapter extends RecyclerView.Adapter {

    private static final int SENT = 1;
    private static final int RECEIVED = 2;

    private List<MessageItem> messageItems;
    private Context context;

    public void setMessageItems(List<MessageItem> items) {
        messageItems = items;
    }

    public MessagesListAdapter(Context context, List<MessageItem> messageItems) {
        this.messageItems = messageItems;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if(viewType == SENT){
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_message_right,
                    parent, false);
            return new SentMessageHolder(itemView);
        } else if(viewType == RECEIVED){
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_message_left,
                    parent, false);
            return new ReceivedMessageHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(messageItems != null){
            MessageItem current = messageItems.get(position);
            switch (holder.getItemViewType()) {
                case SENT:
                    ((SentMessageHolder) holder).bind(current);
                    break;
                case RECEIVED:
                    ((ReceivedMessageHolder) holder).bind(current);
            }
        }
    }

    public void clear() {
        messageItems.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return messageItems.size();
    }


    @Override
    public int getItemViewType(int position){
        MessageItem messageItem = messageItems.get(position);
        if(messageItem.getSent())
            return SENT;
        return RECEIVED;
    }


    public class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        SentMessageHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.message_sent);
            timeText = itemView.findViewById(R.id.sent_time);
        }

        public void bind(MessageItem message) {
            messageText.setText(message.getContent());
            timeText.setText((message.getCreated()));
        }
    }

    public class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        ReceivedMessageHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.message_received);
            timeText = itemView.findViewById(R.id.received_time);
        }

        public void bind(MessageItem message) {
            messageText.setText(message.getContent());
            timeText.setText((message.getCreated()));
        }
    }

}
