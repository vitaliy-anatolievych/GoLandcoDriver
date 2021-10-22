package com.golandco.golandcodriver.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.golandco.golandcodriver.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startMapActivity()
    }

    private fun startMapActivity() {
        val mapIntent = Intent(this, MapsActivity::class.java)
        startActivity(mapIntent)
    }

}