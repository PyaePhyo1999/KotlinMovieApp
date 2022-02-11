package com.example.mymovie.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mymovie.AppUtils
import com.example.mymovie.login.Login
import com.example.mymovie.login.Register
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

open class BaseActivity : AppCompatActivity() {


    private var auth: FirebaseAuth = Firebase.auth
    private lateinit var googleSignInClient: GoogleSignInClient
    fun toMainActivity(){
        val intent = MainActivity.intent(applicationContext)
        startActivity(intent)
    }


    fun toLoginActivity() {
        val intent = Login.intent(applicationContext)
        startActivity(intent)
    }

    fun navBookmark() {
        Toast.makeText(this, "bookmarkActivity", Toast.LENGTH_SHORT).show()
        val intent = BookMarkActivity.intent(applicationContext)
        startActivity(intent)
    }

    fun logout() {
        signOut()
        toLoginActivity()
    }

    private fun signOut(){
        auth.signOut()
        createRequest()
        googleSignInClient.signOut().addOnCompleteListener{
            auth.currentUser?.delete()
         }


    }
//To Register Activity
     fun createAccount() {
        val intent = Register.intent(applicationContext)
        startActivity(intent)
    }

// Configure Google Sign In
     fun createRequest() {

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("906107109834-77u8a2vq4qm82s5ovhvdft4g0lahp47n.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

    }


//Sign In Intent
     fun signIn(){
        val signInIntent = googleSignInClient.signInIntent
        startActivityIfNeeded(signInIntent, AppUtils.RC_SIGN_IN)
    }
}