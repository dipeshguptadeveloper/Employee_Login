package com.dkgtech.notefirebasemvvm.screen.PhoneLoginActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.dkgtech.notefirebasemvvm.databinding.ActivityPhoneLoginBinding
import com.dkgtech.notefirebasemvvm.screen.OtpActivity.OtpActivity
import com.dkgtech.notefirebasemvvm.screen.RegisterOptionActivity.RegisterOptionActivity
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

class PhoneLoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityPhoneLoginBinding

    lateinit var auth: FirebaseAuth

    lateinit var mVerificationId: String


    companion object {
        val TAG = "PhoneLoginActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhoneLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        with(binding) {

            btnContinue.setOnClickListener {

                var phone = edtPhone.text.toString()

                if (phone != "" && phone.length >= 10) {
                    phone = "+91${phone}"

                    val options = PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber(phone) // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this@PhoneLoginActivity) // Activity (for callback binding)
                        .setCallbacks(
                            object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                                override fun onVerificationCompleted(credential: PhoneAuthCredential) {

                                }

                                override fun onVerificationFailed(e: FirebaseException) {
                                    // This callback is invoked in an invalid request for verification is made,
                                    // for instance if the the phone number format is not valid.
                                    Log.w(TAG, "onVerificationFailed", e)

                                    if (e is FirebaseAuthInvalidCredentialsException) {
                                        // Invalid request
                                    } else if (e is FirebaseTooManyRequestsException) {
                                        // The SMS quota for the project has been exceeded
                                    } else if (e is FirebaseAuthMissingActivityForRecaptchaException) {
                                        // reCAPTCHA verification attempted with null Activity
                                    }

                                    // Show a message and update the UI
                                }

                                override fun onCodeSent(
                                    verificationId: String,
                                    token: PhoneAuthProvider.ForceResendingToken,
                                ) {
                                    // The SMS verification code has been sent to the provided phone number, we
                                    // now need to ask the user to enter the code and then construct a credential
                                    // by combining the code with a verification ID.
                                    Log.d(TAG, "onCodeSent:$verificationId")
                                    startActivity(
                                        Intent(
                                            this@PhoneLoginActivity,
                                            OtpActivity::class.java
                                        )
                                    )
                                    // Save verification ID and resending token so we can use them later
                                    mVerificationId = verificationId
                                }
                            }
                        ) // OnVerificationStateChangedCallbacks
                        .build()
                    PhoneAuthProvider.verifyPhoneNumber(options)


                } else {
                    Toast.makeText(
                        this@PhoneLoginActivity,
                        "Enter valid phone no.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            llLogin.setOnClickListener {
                startActivity(Intent(this@PhoneLoginActivity, RegisterOptionActivity::class.java))
            }

        }

    }
}