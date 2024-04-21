package com.example.messegerkotlin.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ChatViewFactory(private val currentId: String, private val outherId: String): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ChatViewModel(currentId, outherId) as T
    }
}