package com.capstone.nusart.ui_page.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import com.capstone.nusart.R
import com.capstone.nusart.databinding.ActivityMainBinding
import com.capstone.nusart.ui_page.ui.camera.CameraFragment
import com.capstone.nusart.ui_page.ui.home.HomeFragment
import com.capstone.nusart.ui_page.ui.map.MapFragment
import com.capstone.nusart.ui_page.ui.profile.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val navigation: BottomNavigationView = findViewById(R.id.bottom_navigation)
        navigation.setOnItemSelectedListener(itemSelectedListener)
    }

    private val itemSelectedListener = NavigationBarView.OnItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_home -> {
                val fragment = HomeFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.ini_activity_main, fragment, fragment::class.java.simpleName)
                    .commit()
                return@OnItemSelectedListener true
            }
            /*
            R.id.nav_camera-> {
                val fragment = CameraFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.ini_activity_camera, fragment,fragment::class.java.simpleName)
                    .commit()
                return@OnItemSelectedListener true
            }*/
            R.id.nav_map-> {
                val fragment = MapFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.ini_activity_main, fragment, fragment::class.java.simpleName)
                    .commit()
                return@OnItemSelectedListener true
            }

            R.id.nav_profile -> {
                val fragment = ProfileFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.ini_activity_main, fragment, fragment::class.java.simpleName)
                    .commit()
                return@OnItemSelectedListener true
            }
        }
        false
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}