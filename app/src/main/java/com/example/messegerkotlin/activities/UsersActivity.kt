package com.example.messegerkotlin.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.messegerkotlin.R
import com.example.messegerkotlin.User
import com.example.messegerkotlin.adapters.UsersAdapter
import com.example.messegerkotlin.databinding.ActivityUsersBinding
import com.example.messegerkotlin.viewModels.SignInViewModel
import com.example.messegerkotlin.viewModels.UsersViewModel
import com.google.firebase.auth.FirebaseUser

class UsersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUsersBinding
    private lateinit var viewModel: UsersViewModel
    private lateinit var adapter: UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(UsersViewModel::class.java)
        adapter = UsersAdapter()
        val currentId = intent.getStringExtra(EXTRA_USER)

        binding.recyclerViewUsers.adapter = adapter
        binding.recyclerViewUsers.layoutManager = LinearLayoutManager(this)

        viewModel.user.observe(this, Observer {
            Log.d("problemsss", "UsersActivity")
            if (it == null){
                startActivity(SignInActivity.newIntent(this))
                finish()
            }
        })

        viewModel.users.observe(this, Observer {
            adapter.users = it
        })

        adapter.onUserClickListener = object : UsersAdapter.OnUserClickListener{
            override fun onUserClick(user: User) {
                if (currentId != null) {
                    startActivity(ChatActivity.newIntent(this@UsersActivity, currentId, user.id))
                }
            }
        }
    }


    companion object{

        const val EXTRA_USER = "extra_user"

        fun newIntent(context: Context, id: String): Intent{
            val intent = Intent(context, UsersActivity::class.java)
            intent.putExtra(EXTRA_USER, id)
            return intent
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.itemMenu){
            viewModel.signOut()
        }
        return super.onOptionsItemSelected(item)
    }
}