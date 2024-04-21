package com.example.messegerkotlin.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.messegerkotlin.databinding.ActivitySignInBinding
import com.example.messegerkotlin.viewModels.SignInViewModel

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var viewModel: SignInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[SignInViewModel::class.java]
        setContentView(binding.root)


        viewModel.user.observe(this, Observer {
            if (it != null){
                startActivity(UsersActivity.newIntent(this, it.uid))
                Log.d("problemsss", "SignIn")
                finish()
            }
        })

        viewModel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })

        with(binding){
            buttonLogin.setOnClickListener {
                val login = editTextEmail.text.toString()
                val password = editTextPassword.text.toString()
                viewModel.signIn(login, password)
            }
            textViewFogotPassword.setOnClickListener {
                startActivity(RestorePasswordActivity.newIntent(this@SignInActivity))
            }

            textViewRegistr.setOnClickListener {
                startActivity(RegistrationActivity.newIntent(this@SignInActivity))
            }
        }

    }

    companion object{
        fun newIntent(context: Context): Intent{
            return Intent(context, SignInActivity::class.java)
        }
    }
}