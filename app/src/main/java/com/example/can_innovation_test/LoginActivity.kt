package com.example.can_innovation_test

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.can_innovation_test.databinding.ActivityLoginBinding
import com.example.can_innovation_test.roomData.UserDatabase
import com.example.can_innovation_test.roomData.UserRepository
import com.example.can_innovation_test.viewmodel.LoginViewModel
import com.example.can_innovation_test.viewmodel.ViewModelFactory


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var alertDialog: AlertDialog? = null
    private lateinit var viewModel: LoginViewModel
    private var passAuth: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val db = Room.databaseBuilder(
            applicationContext,
            UserDatabase::class.java, "userData"
        ).allowMainThreadQueries().build()
        val userDao = db.userDao()
        val repository = UserRepository(userDao)
        val viewModelFactory = ViewModelFactory { LoginViewModel(repository) }
        viewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)
        viewModel.checkDB()

        observe()
        applyData()

    }

    override fun onRestart() {
        super.onRestart()
        viewModel.checkDB()
    }

    private fun applyData() {
        binding.apply {
            username.editText1.hint = "Username"
            password.editText1.apply {
                hint = "Password"
                inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            exitButtoon.setOnClickListener {
                finish()
            }
            regisButton.setOnClickListener {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
            }
            loginButton.setOnClickListener {
                val usernameText = username.editText1.text
                val passwordText = password.editText1.text
                when {
                    usernameText.isEmpty() || passwordText.isEmpty() -> {
                        val builder = AlertDialog.Builder(this@LoginActivity)
                        builder.setTitle("Warning!")
                        builder.setMessage("Please ensure to fill Username and Password")
                        builder.setPositiveButton("OK") { _, _ ->
                        }
                        alertDialog = builder.create()
                        alertDialog?.show()
                    }
                    else -> {
                        viewModel.checkUserPass(usernameText.toString(), passwordText.toString())
                        if (!passAuth) {
                            val builder = AlertDialog.Builder(this@LoginActivity)
                            builder.setTitle("Error!")
                            builder.setMessage("Something went wrong sorry please try again!!")
                            builder.setPositiveButton("OK") { _, _ ->
                            }
                            alertDialog = builder.create()
                            alertDialog?.show()
                        } else {
                            val intent = Intent(this@LoginActivity, MainPageActivity::class.java)
                            val bundle = Bundle()
                            bundle.putString("username", username.editText1.text.toString())
                            intent.putExtras(bundle)
                            startActivity(intent)
                        }
                    }
                }


            }
        }
    }

    private fun observe() {
        viewModel.userLiveData.observe(this) { user ->
            passAuth = when (user) {
                null -> {
                    false
                }
                else -> {
                    true
                }
            }
        }

        viewModel.rowCountLiveData.observe(this) { count ->
            binding.apply {
                registerText.visibility = if (count <= 0) View.VISIBLE else View.GONE
                username.editText1.visibility = if (count <= 0) View.GONE else View.VISIBLE
                password.editText1.visibility = if (count <= 0) View.GONE else View.VISIBLE
                loginButton.visibility = if (count <= 0) View.GONE else View.VISIBLE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Dismiss the dialog if it's still showing
        alertDialog?.dismiss()
    }
}