package com.example.whatsappclone.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.whatsappclone.MainActivity
import com.example.whatsappclone.databinding.ActivityNumberBinding
import com.google.firebase.auth.FirebaseAuth

class NumberActivity : AppCompatActivity() {
    private lateinit var binding : ActivityNumberBinding
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        if(auth.currentUser !=null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

        binding.button6.setOnClickListener {
            if(binding.phoneNumber.text!!.isEmpty() || binding.phoneNumber.text!!.length != 10){
                Toast.makeText(this,"Please enter your number!!",Toast.LENGTH_SHORT).show()
            }
            else{
                var intent = Intent(this,OTPActivity::class.java)
                intent.putExtra("number",binding.phoneNumber.text!!.toString())
                startActivity(intent)
            }
        }
    }
}