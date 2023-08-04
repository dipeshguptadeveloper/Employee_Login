package com.dkgtech.notefirebasemvvm.screen.LoginActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.dkgtech.notefirebasemvvm.screen.MainActivity.MainActivity
import com.dkgtech.notefirebasemvvm.screen.SignUpActivity.SignUpActivity
import com.dkgtech.notefirebasemvvm.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding

    lateinit var firebaseDB: FirebaseFirestore

    lateinit var auth: FirebaseAuth

    companion object {
        val TAG = "LoginActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseDB = Firebase.firestore
        auth = Firebase.auth

        with(binding) {

            btnLogin.setOnClickListener {

                val email = edtEmail.text.toString()
                val password = edtPassword.text.toString()

                if (email != "" && password != "") {
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnSuccessListener {
                            Toast.makeText(
                                this@LoginActivity,
                                "Logged-In Successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            finish()
                        }
                        .addOnFailureListener {
                            Log.d(TAG, "Error : ${it.message}")
                            it.printStackTrace()
                        }
                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        "Please enter email id & password",
                        Toast.LENGTH_SHORT
                    ).show()

                }

            }

            llSignUp.setOnClickListener {
                startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
            }

        }

    }
}