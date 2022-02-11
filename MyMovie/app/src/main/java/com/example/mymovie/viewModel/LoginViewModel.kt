package com.example.mymovie.viewModel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.mymovie.event.LoginLiveEvent
import com.example.mymovie.event.SingleLiveEvent
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    val login = SingleLiveEvent<LoginLiveEvent>()

    fun login(email: String, password: String) {

        if (email == "") {
            login.postValue(LoginLiveEvent.Error("email cannot be empty"))
            return
        }

        if (password == "") {
            login.postValue(LoginLiveEvent.Error("Password cannot be empty"))
            return
        }

        if (password!=password){
            login.postValue(LoginLiveEvent.Error("Wrong Password"))
            return
        }


        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {

            if (it.isSuccessful) {
                login.postValue(LoginLiveEvent.Success)
            } else {
                Log.w("Login", "createUserWithEmail:failure", it.exception);
                login.postValue(LoginLiveEvent.Error("Authentication failed"))

            }
        }
    }


    fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    login.postValue(LoginLiveEvent.GoogleLoginSuccess(account))

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", it.exception)
                    login.postValue(LoginLiveEvent.Error("Authentication failed"))

                }
            }
    }


}