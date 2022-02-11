package com.example.mymovie.event

import com.google.android.gms.auth.api.signin.GoogleSignInAccount

sealed class LoginLiveEvent {
    object Success : LoginLiveEvent()
    data class GoogleLoginSuccess(val account: GoogleSignInAccount) : LoginLiveEvent()
    data class Error(val message : String ) : LoginLiveEvent()
}