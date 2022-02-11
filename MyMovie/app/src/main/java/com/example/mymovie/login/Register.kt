package com.example.mymovie.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.mymovie.activity.BaseActivity
import com.example.mymovie.activity.MainActivity
import com.example.mymovie.databinding.ActivityRegisterBinding
import com.example.mymovie.event.RegisterLiveEvent
import com.example.mymovie.viewModel.RegisterViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Register : BaseActivity() {
    private lateinit var binding: ActivityRegisterBinding

    private val vmRegister : RegisterViewModel by viewModels()

    companion object {
        fun intent(context: Context): Intent {
            return Intent(context, Register::class.java)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        vmRegister.register.observe(this){
               when(it){
                   RegisterLiveEvent.Success->{
                       toMainActivity()
                       Toast.makeText(
                           this, "Login Success",
                           Toast.LENGTH_SHORT
                       ).show()
                   }
                       is RegisterLiveEvent.Error->{
                           Toast.makeText(
                               this, it.message,
                               Toast.LENGTH_SHORT
                           ).show()
                       }
               }
        }


        binding.apply {
            btnSignUp.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                val cfPassword = etPasswordAgain.text.toString()

                vmRegister.register(email,password,cfPassword)
            }

            btnToLogin.setOnClickListener {
                toLoginActivity()
                finishAffinity()
            }

        }


    }

}