package com.example.mymovie.login

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.mymovie.AppUtils.Companion.RC_SIGN_IN
import com.example.mymovie.activity.BaseActivity
import com.example.mymovie.activity.MainActivity
import com.example.mymovie.databinding.ActivityLoginBinding
import com.example.mymovie.event.LoginLiveEvent
import com.example.mymovie.viewModel.LoginViewModel
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Login : BaseActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var auth: FirebaseAuth = Firebase.auth
    private val vmLogin: LoginViewModel by viewModels()

    companion object {
        fun intent(context: Context): Intent {
            return Intent(context, Login::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

//Login View Model observe
        vmLogin.login.observe(this) { event ->
            when (event) {
                LoginLiveEvent.Success -> {
                    toMainActivity()
                    Toast.makeText(
                        this, "Login Success",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is LoginLiveEvent.Error -> {
                    Toast.makeText(
                        this, event.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
                is LoginLiveEvent.GoogleLoginSuccess->{
                    toMainActivity()
                    finishAffinity()
                    Toast.makeText(
                        this, "Login Success",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }


        binding.apply {

            btnSignIn.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()

                vmLogin.login(email, password)

            }
            btnGoogle.setOnClickListener {
                    createRequest()
                    signIn()

            }

            btnToRegister.setOnClickListener {
                createAccount()
            }
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data!!)

            try {
                // Google Sign In was successful, authenticate with Firebase
                    if (result!!.isSuccess){
                        val account = result.signInAccount
                        vmLogin.firebaseAuthWithGoogle(account!!)
                    }
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                Toast.makeText(this, "Sign In failed", Toast.LENGTH_LONG).show()
            }
        }
    }


}