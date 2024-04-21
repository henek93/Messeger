package com.example.messegerkotlin.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.messegerkotlin.Messege
import com.example.messegerkotlin.adapters.ChatAdapter
import com.example.messegerkotlin.databinding.ActivityChatBinding
import com.example.messegerkotlin.viewModels.ChatViewFactory
import com.example.messegerkotlin.viewModels.ChatViewModel

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var viewModel: ChatViewModel
    private lateinit var viewModelFactory: ChatViewFactory
    private lateinit var adapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentId= intent.getStringExtra(CURRENT_ID_EXTRA)
        val outherId = intent.getStringExtra(OUTHER_ID_EXTRA)
        adapter = ChatAdapter(currentId, outherId)

        if (currentId != null && outherId != null) {
            viewModelFactory = ChatViewFactory(currentId, outherId)
            viewModel = ViewModelProvider(this, viewModelFactory).get(ChatViewModel::class.java)
        }


        viewModel.error.observe(this, Observer {
            Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
        })
        viewModel.outherUser.observe(this, Observer {
            binding.textViewTitleChat.text = "${it.name} ${it.lastName}"
        })
        viewModel.messeges.observe(this, Observer {
            adapter.messeges = it
        })

        with(binding){
            recycklerViewMesseges.adapter = adapter
            recycklerViewMesseges.layoutManager = LinearLayoutManager(this@ChatActivity)
            imageViewSendMessege.setOnClickListener {
                if (currentId != null && outherId != null){
                    val messege = Messege(currentId, outherId, editTextMessege.text.trim().toString())
                    viewModel.sendMessege(messege)
                }
            }
        }

        viewModel.messegeSend.observe(this, Observer {
            if (it){
                binding.editTextMessege.setText("")
            }
        })
    }


    companion object{

        const val CURRENT_ID_EXTRA = "currentIdExtra"
        const val OUTHER_ID_EXTRA = "outherIdExtra"

        fun newIntent(context: Context, cuurentId: String, outherId: String): Intent{
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra(CURRENT_ID_EXTRA, cuurentId)
            intent.putExtra(OUTHER_ID_EXTRA, outherId)
            return intent
        }
    }
}