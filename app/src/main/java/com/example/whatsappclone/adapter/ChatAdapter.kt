package com.example.whatsappclone.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.whatsappclone.R
import com.example.whatsappclone.activity.ChatActivity
import com.example.whatsappclone.databinding.UserChatRecyclerLayoutBinding
import com.example.whatsappclone.model.UserModel

class ChatAdapter(private val userList : ArrayList<UserModel>,var context : Context): RecyclerView.Adapter<ChatAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.user_chat_recycler_layout,parent,false))
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val itemsViewModel  = userList[position]
        Glide.with(context).load(itemsViewModel.imageUrl).into(holder.binding.userImage)
        holder.binding.userName.text = itemsViewModel.name
        holder.itemView.setOnClickListener {
            val intent = Intent(context,ChatActivity::class.java)
            intent.putExtra("uid",itemsViewModel.uid)
            context.startActivity(intent)
        }

    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var binding : UserChatRecyclerLayoutBinding = UserChatRecyclerLayoutBinding.bind(itemView)

    }
}