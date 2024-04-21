package com.example.messegerkotlin.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth

class RestorePasswordViewModel(application: Application) : AndroidViewModel(application) {

    private val auth = FirebaseAuth.getInstance()
    var isSucces = MutableLiveData<Boolean>()
    var error = MutableLiveData<String>()

    fun restorPassword(email: String){
        auth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                isSucces.value = true
            }
            .addOnFailureListener{
                error.value = it.message.toString()
            }
    }


}