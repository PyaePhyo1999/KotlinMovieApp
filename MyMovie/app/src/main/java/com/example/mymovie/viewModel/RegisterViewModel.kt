package com.example.mymovie.viewModel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.mymovie.event.LoginLiveEvent
import com.example.mymovie.event.RegisterLiveEvent
import com.example.mymovie.event.SingleLiveEvent
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    val register = SingleLiveEvent<RegisterLiveEvent>()

    fun register(email: String, password: String,cfPassword : String) {
        if (email == "" ) {
            register.postValue(RegisterLiveEvent.Error("email cannot be empty"))
            return
        }

        if (password == "" ) {
            register.postValue(RegisterLiveEvent.Error("Password cannot be empty"))
            return
        }
        if (cfPassword == "" ) {
            register.postValue(RegisterLiveEvent.Error("Confirm Password cannot be empty"))
            return
        }

        if (password != cfPassword) {
            register.postValue(RegisterLiveEvent.Error("Password do not match"))
            return
        }
        if (password != cfPassword) {
            register.postValue(RegisterLiveEvent.Error("Password do not match"))
            return
        }
        if (password.length <=6){
            register.postValue(RegisterLiveEvent.Error("Password has to be more than 6 characters"))
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                     when{
                        it.isSuccessful -> {
                            register.postValue(RegisterLiveEvent.Success)
                        }

                        else -> {
                            Log.w("Register", "createUserWithEmail:failure", it.exception);
                            register.postValue(RegisterLiveEvent.Error("Authentication failed"))

                        }
                    }


            }

    }

}