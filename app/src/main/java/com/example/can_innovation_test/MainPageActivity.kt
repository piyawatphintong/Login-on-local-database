package com.example.can_innovation_test

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.can_innovation_test.databinding.ActivityMainPageBinding
import com.example.can_innovation_test.roomData.UserDatabase
import com.example.can_innovation_test.roomData.UserRepository
import com.example.can_innovation_test.viewmodel.MainPageViewModel
import com.example.can_innovation_test.viewmodel.ViewModelFactory

class MainPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainPageBinding
    private lateinit var db: UserDatabase
    private lateinit var viewModel: MainPageViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initDatabase()
        val userDao = db.userDao()
        val repository = UserRepository(userDao)
        val viewModelFactory = ViewModelFactory { MainPageViewModel(repository) }
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainPageViewModel::class.java)
        val bundle = intent.extras
        bundle?.getString("username")?.let { viewModel.getUserData(it) }
        observe()
        binding.logoutButton.apply {
            setOnClickListener {
                val intent = Intent(this@MainPageActivity, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
        }

    }

    private fun initDatabase() {
        db = Room.databaseBuilder(
            applicationContext,
            UserDatabase::class.java, "userData"
        ).allowMainThreadQueries().build()
    }

    private fun observe() {
//        binding.apply {
//            viewModel.apply {
//                name.observe(this@MainPageActivity) { name ->
//                    mainFullName.text = name
//                }
//                email.observe(this@MainPageActivity) { email ->
//                    mainEmail.text = email
//                }
//                dob.observe(this@MainPageActivity) { dob ->
//                    mainDob.text = dob
//                }
//                phoneNum.observe(this@MainPageActivity) { phoneNum ->
//                    mainPhoneNum.text = phoneNum
//
//                }
//            }
//
//        }
        viewModel.apply {
            name.observe(this@MainPageActivity) { name ->
                binding.mainFullName.text = "Username: " + name
            }
            email.observe(this@MainPageActivity) { email ->
                binding.mainEmail.text = "Email: " + email
            }
            dob.observe(this@MainPageActivity) { dob ->
                binding.mainDob.text = "Birth Date: " + dob
            }
            phoneNum.observe(this@MainPageActivity) { phoneNum ->
                binding.mainPhoneNum.text = "Phone Number: " + phoneNum
            }
        }
    }
}