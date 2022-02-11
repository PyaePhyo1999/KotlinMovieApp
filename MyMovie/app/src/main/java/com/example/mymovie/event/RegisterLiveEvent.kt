package com.example.mymovie.event

sealed class RegisterLiveEvent {
    object Success : RegisterLiveEvent()
    data class Error(val message : String ) : RegisterLiveEvent()
}