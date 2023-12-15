package com.capstone.nusart.ui_page.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.nusart.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}