package com.example.messegerkotlin.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.messegerkotlin.R
import com.example.messegerkotlin.databinding.ActivityRestorePasswordBinding
import com.example.messegerkotlin.viewModels.RestorePasswordViewModel

class RestorePasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRestorePasswordBinding
    private lateinit var viewModel: RestorePasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestorePasswordBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(RestorePasswordViewModel::class.java)
        setContentView(binding.root)

        with(binding){
            val email = editTextText.text.trim().toString()
            viewModel.restorPassword(email)
        }
        viewModel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })

        viewModel.isSucces.observe(this, Observer {
            if (it){
                startActivity(SignInActivity.newIntent(this))
                finish()
            }

        })
    }

    companion object{

        fun newIntent(context: Context): Intent{
            return Intent(context, RegistrationActivity::class.java)
        }
    }
}