package com.dkgtech.notefirebasemvvm.screen.SignUpActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.dkgtech.notefirebasemvvm.databinding.ActivitySignUpBinding
import com.dkgtech.notefirebasemvvm.model.UserModel
import com.dkgtech.notefirebasemvvm.screen.LoginActivity.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignUpBinding

    lateinit var firebaseDB: FirebaseFirestore

    lateinit var auth: FirebaseAuth

    companion object {
        val TAG = "SignUpActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseDB = Firebase.firestore
        auth = Firebase.auth

        with(binding) {

            btnSignUp.setOnClickListener {

                val email = edtEmail.text.toString()
                val password = edtPassword.text.toString()

                if (email != "" && password != "") {

                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnSuccessListener {
                            Toast.makeText(
                                this@SignUpActivity,
                                "Account Created Successfully",
                                Toast.LENGTH_SHORT
                            ).show()

                            val userModel = UserModel(it.user!!.uid, email, password)

                            firebaseDB
                                .collection("users")
                                .document(it.user!!.uid)
                                .set(userModel)
                                .addOnSuccessListener {
                                    startActivity(
                                        Intent(
                                            this@SignUpActivity,
                                            LoginActivity::class.java
                                        )
                                    )
                                    finish()
                                }
                                .addOnFailureListener {
                                    Log.d(TAG, "Error : ${it.message}")
                                    it.printStackTrace()
                                }


                        }
                        .addOnFailureListener {
                            Log.d(TAG, "Error : ${it.message}")
                            it.printStackTrace()
                        }

                } else {
                    Toast.makeText(
                        this@SignUpActivity,
                        "Please enter email id & password",
                        Toast.LENGTH_SHORT
                    ).show()

                }

            }

            llLogin.setOnClickListener {
                startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
            }

        }

    }
}