package com.example.whatsappclone.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.whatsappclone.adapter.MessageAdapter
import com.example.whatsappclone.databinding.ActivityChatBinding
import com.example.whatsappclone.model.MessageModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Date

class ChatActivity : AppCompatActivity() {
     private lateinit var binding: ActivityChatBinding
     private lateinit var database: FirebaseDatabase

     private lateinit var senderUid: String
     private lateinit var receiverUid: String

     private lateinit var senderRoom: String
     private lateinit var receiverRoom: String
     private lateinit var auth: FirebaseAuth

     private lateinit var list : ArrayList<MessageModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        senderUid = auth.currentUser!!.uid.toString()
        receiverUid = intent.getStringExtra("uid")!!

        senderRoom = senderUid + receiverUid
        receiverRoom = receiverUid + senderUid
        list = ArrayList()

        binding.sendBtn.setOnClickListener{
            if(binding.message!!.text.isEmpty()){
                Toast.makeText(this,"Please Type your message",Toast.LENGTH_SHORT).show()
            }else{
                val message = MessageModel(binding.message.text.toString(),senderUid, Date().time)

                val randomKey = database.reference.push().key
                database.reference.child("chats")
                    .child(senderRoom).child("message").child(randomKey!!).setValue(message)
                    .addOnSuccessListener {
                        binding.message.text = null
                    }
            }
        }
        database.reference.child("chats").child(senderRoom).child("message")
            .addValueEventListener(object :ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    list.clear()

                    for(snapshot1 in snapshot.children){
                        val data = snapshot1.getValue(MessageModel::class.java)
                        list.add(data!!)
                    }

                    binding.recyclerChat.adapter = MessageAdapter(list,this@ChatActivity)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }
}