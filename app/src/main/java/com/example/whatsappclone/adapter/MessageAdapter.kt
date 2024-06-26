package com.example.whatsappclone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.whatsappclone.R
import com.example.whatsappclone.databinding.ActivityChatBinding
import com.example.whatsappclone.databinding.ReceiverChatBinding
import com.example.whatsappclone.databinding.SenderChatBinding
import com.example.whatsappclone.databinding.UserChatRecyclerLayoutBinding
import com.example.whatsappclone.model.MessageModel
import com.example.whatsappclone.model.UserModel
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(private val messageList : ArrayList<MessageModel>, var context : Context):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var ITEM_SENT = 1
    var ITEM_RECEIVED = 2
    inner class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var binding : SenderChatBinding= SenderChatBinding.bind(itemView)

    }
    inner class ReceiverViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var binding : ReceiverChatBinding = ReceiverChatBinding.bind(itemView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return if(viewType == ITEM_SENT)
                SentViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.sender_chat,parent,false))
            else ReceiverViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.receiver_chat,parent,false))
    }

    override fun getItemViewType(position: Int): Int {
        return if (FirebaseAuth.getInstance().uid == messageList[position].senderId) ITEM_SENT else ITEM_RECEIVED
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messageList[position]
        if(holder.itemViewType == ITEM_SENT){
            val viewHolder = holder as SentViewHolder
            viewHolder.binding.senderMsg.text = message.message
        }
        else{
            val viewHolder = holder as ReceiverViewHolder
            viewHolder.binding.receiverMsg.text = message.message
        }

    }
}