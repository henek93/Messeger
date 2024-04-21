package com.example.messegerkotlin.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.messegerkotlin.Messege
import com.example.messegerkotlin.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChatViewModel(curerndId: String, outherId: String) : ViewModel() {

    private val database = FirebaseDatabase.getInstance()
    private val referenceMesseges = database.getReference("Messege")
    private val referenceUsers = database.getReference("Users")

    var outherUser = MutableLiveData<User>()
    var messeges = MutableLiveData<List<Messege?>>()
    var error = MutableLiveData<String>()
    var messegeSend = MutableLiveData<Boolean>()

    init {
        referenceUsers.child(outherId).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                outherUser.value = snapshot.getValue(User::class.java)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        referenceMesseges.child(curerndId).child(outherId).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val listMess = ArrayList<Messege?>()
                for (data in snapshot.children){
                    listMess.add(data.getValue(Messege::class.java))
                }
                messeges.value = listMess
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    fun sendMessege(messege: Messege){
        referenceMesseges.child(messege.sendertId)
            .child(messege.reciverid)
            .push()
            .setValue(messege)
            .addOnSuccessListener {
                referenceMesseges.child(messege.reciverid)
                    .child(messege.sendertId)
                    .push()
                    .setValue(messege)
                    .addOnSuccessListener {
                        messegeSend.value = true
                    }
                    .addOnFailureListener {
                        error.value = it.message
                    }
            }
            .addOnFailureListener {
                error.value = it.message
            }
    }
}