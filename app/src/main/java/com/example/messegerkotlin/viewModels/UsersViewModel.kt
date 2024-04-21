package com.example.messegerkotlin.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.messegerkotlin.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UsersViewModel(application: Application) : AndroidViewModel(application) {

    private val auth = FirebaseAuth.getInstance()
    var users = MutableLiveData<List<User>>()
    var user: MutableLiveData<FirebaseUser?> = MutableLiveData()
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private val referenceUsers = firebaseDatabase.getReference("Users")


    init {
        auth.addAuthStateListener {
            user.value = it.currentUser
            Log.d("problemsss", it.currentUser.toString())
        }

        getUsers()
    }

    fun getUsers(){
        referenceUsers.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = ArrayList<User>()
                val cuurentUser = auth.currentUser ?: return
                for (data in snapshot.children){
                    val classUser = data.getValue(User::class.java)
                    if (classUser == null){
                        return
                    }
                    if (classUser.id != cuurentUser.uid){
                        classUser.let {
                            list.add(it)
                        }
                    }
                }
                users.value = list.toList()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    fun signOut(){
        auth.signOut()
    }
}