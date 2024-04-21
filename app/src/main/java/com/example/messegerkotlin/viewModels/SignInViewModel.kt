package com.example.messegerkotlin.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SignInViewModel(application: Application) : AndroidViewModel(application) {

    private var firebaseAuth: FirebaseAuth? = null
    var user = MutableLiveData<FirebaseUser?>(null)
    var error = MutableLiveData<String>()

    init {
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth?.addAuthStateListener {
            user.value = it.currentUser
            Log.d("problemsss", it.currentUser.toString() + "SignIn")
        }
    }

    fun signIn(login: String, password: String){
        firebaseAuth?.signInWithEmailAndPassword(login, password)
            ?.addOnFailureListener({
                error.value = it.message.toString()
            })
    }

    fun signOut(){
        firebaseAuth?.signOut()
    }
}