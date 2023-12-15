package com.capstone.nusart.ui_page.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.nusart.R
import com.capstone.nusart.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}