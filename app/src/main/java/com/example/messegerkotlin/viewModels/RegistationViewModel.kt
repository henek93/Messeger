package com.example.messegerkotlin.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.messegerkotlin.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegistationViewModel(application: Application) : AndroidViewModel(application) {

    private val auth = FirebaseAuth.getInstance()
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private lateinit var referenceUsers: DatabaseReference
    var error = MutableLiveData<String>()
    var user = MutableLiveData<FirebaseUser>()


    init {
        auth.addAuthStateListener {
            if (it.currentUser != null){
                user.value = it.currentUser
            }
        }
        referenceUsers = firebaseDatabase.getReference("Users")
    }

    fun registr(
        login: String,
        password: String,
        name: String,
        lastName: String,
        age: String,
        online: Boolean
    ) {
        auth.createUserWithEmailAndPassword(login, password)
            .addOnSuccessListener {
                val user1 = it.user
                if (user1 != null) {
                    val newUser = User(user1.uid, name, lastName, age, online)
                    referenceUsers.child(user1.uid).setValue(newUser)
                }
            }
            .addOnFailureListener {
                error.value = it.message.toString()
            }
    }


}