package com.dkgtech.notefirebasemvvm.screen.RegisterOptionActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dkgtech.notefirebasemvvm.databinding.ActivityRegisterOptionBinding
import com.dkgtech.notefirebasemvvm.screen.LoginActivity.LoginActivity
import com.dkgtech.notefirebasemvvm.screen.PhoneLoginActivity.PhoneLoginActivity
import com.dkgtech.notefirebasemvvm.screen.SignUpActivity.SignUpActivity

class RegisterOptionActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegisterOptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterOptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {

            btnPhone.setOnClickListener {
                startActivity(Intent(this@RegisterOptionActivity, PhoneLoginActivity::class.java))
            }

            btnEmail.setOnClickListener {
                startActivity(Intent(this@RegisterOptionActivity, SignUpActivity::class.java))
            }

            llLogin.setOnClickListener {
                startActivity(Intent(this@RegisterOptionActivity, LoginActivity::class.java))
            }

        }

    }

}