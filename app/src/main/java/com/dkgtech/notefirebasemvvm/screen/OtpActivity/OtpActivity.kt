package com.dkgtech.notefirebasemvvm.screen.OtpActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dkgtech.notefirebasemvvm.databinding.ActivityOtpBinding
import com.dkgtech.notefirebasemvvm.screen.MainActivity.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class OtpActivity : AppCompatActivity() {

    lateinit var binding: ActivityOtpBinding

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        with(binding) {

            btnLogin.setOnClickListener {
                val otpView = otpView.text.toString()

                if (otpView != "") {
                    startActivity(Intent(this@OtpActivity, MainActivity::class.java))
                    finish()

                } else {
                    Toast.makeText(this@OtpActivity, "Enter the OTP", Toast.LENGTH_SHORT).show()
                }
            }


        }

    }
}