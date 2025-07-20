package com.techmatrix18.p5e.chat;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.techmatrix18.p5e.R;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Message> messageList;

    // Конструктор адаптера
    public MessageAdapter(List<Message> messages) {
        this.messageList = messages;
    }

    // Внутренний ViewHolder
    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        public TextView textMessage;
        public LinearLayout messageContainer;

        public MessageViewHolder(View itemView) {
            super(itemView);
            textMessage = itemView.findViewById(R.id.textMessage);
            messageContainer = itemView.findViewById(R.id.messageContainer);
        }
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        Message msg = messageList.get(position);
        holder.textMessage.setText(msg.getText());

        if (msg.isFromUser()) {
            holder.messageContainer.setGravity(Gravity.END); // сообщения пользователя справа
            holder.textMessage.setBackgroundResource(R.drawable.message_bubble); // зеленый фон
        } else {
            holder.messageContainer.setGravity(Gravity.START); // сообщения от собеседника слева
            holder.textMessage.setBackgroundResource(android.R.color.white);
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }
}

