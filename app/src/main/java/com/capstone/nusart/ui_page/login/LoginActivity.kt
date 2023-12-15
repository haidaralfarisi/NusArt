package com.capstone.nusart.ui_page.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.nusart.R
import com.capstone.nusart.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}