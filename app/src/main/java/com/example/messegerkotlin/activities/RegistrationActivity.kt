package com.example.messegerkotlin.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.messegerkotlin.R
import com.example.messegerkotlin.databinding.ActivityRegistrationBinding
import com.example.messegerkotlin.viewModels.RegistationViewModel

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var viewModel: RegistationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[RegistationViewModel::class.java]
        setContentView(binding.root)

        viewModel.user.observe(this, Observer {
            if (it != null){
                startActivity(UsersActivity.newIntent(this, it.uid))
                finish()
            }
        })

        with(binding){
            buttonReg.setOnClickListener {
                val login = editTextEmailReg.text.toString()
                val password = editTextPasswordReg.text.toString()
                val name = editTextNameReg.text.toString()
                val lastName = editTextLastNameReg.text.toString()
                val age = editTextAgeReg.text.toString()
                viewModel.registr(login, password, name, lastName, age, true)
            }
        }

        viewModel.error.observe(this, Observer {
            Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
        })
    }

    companion object{

        fun newIntent(context: Context): Intent {
            return Intent(context, RegistrationActivity::class.java)
        }
    }


}