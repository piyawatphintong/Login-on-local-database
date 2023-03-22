package com.example.can_innovation_test

import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.can_innovation_test.databinding.ActivityRegisterBinding
import com.example.can_innovation_test.roomData.UserDataEntity
import com.example.can_innovation_test.roomData.UserDatabase
import com.example.can_innovation_test.roomData.UserRepository
import com.example.can_innovation_test.viewmodel.RegisterViewModel
import com.example.can_innovation_test.viewmodel.ViewModelFactory

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var db: UserDatabase
    private lateinit var viewModel: RegisterViewModel
    private var alertDialog: AlertDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initDatabase()
        val userDao = db.userDao()
        val repository = UserRepository(userDao)
        val viewModelFactory = ViewModelFactory { RegisterViewModel(repository) }
        viewModel = ViewModelProvider(this, viewModelFactory).get(RegisterViewModel::class.java)

        applyData()
    }

    private fun isFieldValid(editText: EditText): Boolean {
        val text = editText.text.toString().trim()
        return text.isNotEmpty()
    }

    private fun initDatabase() {
        db = Room.databaseBuilder(
            applicationContext,
            UserDatabase::class.java, "userData"
        ).allowMainThreadQueries().build()
    }

    private fun applyData() {
        binding.apply {
            usernameReg.editText1.apply {
                hint = "Username"
            }
            passwordReg.editText1.apply {
                hint = "Password"
            }
            confPass.editText1.apply {
                hint = "ConfirmPassword"
            }
            fullName.editText1.apply {
                hint = "Full name"
            }
            dob.editText1.apply {
                hint = "dd/mm/yyyy"
                inputType = InputType.TYPE_CLASS_DATETIME
            }
            email.editText1.apply {
                hint = "email"
                inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            }
            phoneNum.editText1.apply {
                hint = "Telephone number"
                inputType = InputType.TYPE_CLASS_NUMBER
                filters = arrayOf<InputFilter>(InputFilter.LengthFilter(10))
            }
            regisButton.setOnClickListener {
                if (isFieldValid(usernameReg.editText1) &&
                    isFieldValid(passwordReg.editText1) &&
                    isFieldValid(confPass.editText1) &&
                    isFieldValid(fullName.editText1) &&
                    isFieldValid(dob.editText1) &&
                    isFieldValid(email.editText1) &&
                    isFieldValid(phoneNum.editText1) &&
                    usernameReg.editText1.text.toString() == usernameReg.editText1.text.toString()
                ) {
                    val user = UserDataEntity(
                        _id = phoneNum.editText1.text.toString().toInt(),
                        Username = usernameReg.editText1.text.toString(),
                        Password = confPass.editText1.text.toString(),
                        FullName = fullName.editText1.text.toString(),
                        BirthDate = dob.editText1.text.toString(),
                        Email = email.editText1.text.toString(),
                        TelephoneNumber = phoneNum.editText1.text.toString()
                    )
                    viewModel.insert(user)
                    val intent = Intent(this@RegisterActivity, MainPageActivity::class.java)
                    val bundle = Bundle()
                    bundle.putString("username", usernameReg.editText1.text.toString())

                    startActivity(intent)
                } else {
                    val builder = AlertDialog.Builder(this@RegisterActivity)
                    builder.setTitle("Please Fill all blank space!")
                    builder.setMessage("If all blank space is filled Please check you password")
                    builder.setPositiveButton("OK") { _, _ ->
                    }
                    alertDialog = builder.create()
                    alertDialog?.show()
                }

            }
        }
    }
}