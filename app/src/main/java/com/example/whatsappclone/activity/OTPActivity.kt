package com.example.whatsappclone.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.whatsappclone.R
import com.example.whatsappclone.databinding.ActivityOtpactivityBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class OTPActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOtpactivityBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var verificationId: String
    private lateinit var dialog: AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        val builder = AlertDialog.Builder(this)

        builder.setMessage("Please Wait ...")
        builder.setTitle("Loading")
        builder.setCancelable(false)

        dialog = builder.create()
        dialog.show()

        val phoneNumber = "+91" +intent.getStringExtra("number")


        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(1L,TimeUnit.MINUTES)
            .setActivity(this)
            .setCallbacks(object :PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {

                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    dialog.dismiss()
                    Toast.makeText(this@OTPActivity,"Please Enter A Valid OTP!!",Toast.LENGTH_SHORT).show()
                }

                override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(p0, p1)

                    dialog.dismiss()
                    verificationId = p0
                }

            }).build()


        PhoneAuthProvider.verifyPhoneNumber(options)

        binding.button6.setOnClickListener{
            if(binding.OTP.text!!.isEmpty()){
                Toast.makeText(this@OTPActivity,"Please Enter OTP!!",Toast.LENGTH_SHORT).show()
            }else{
                val crediential = PhoneAuthProvider.getCredential(verificationId,binding.OTP.text.toString())
                auth.signInWithCredential(crediential).addOnCompleteListener {
                    if(it.isSuccessful){
                        startActivity(Intent(this,ProfileActivity::class.java))
                        finish()
                    }else{
                        dialog.dismiss()
                        Toast.makeText(this,"Error ${it.exception}",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }
}