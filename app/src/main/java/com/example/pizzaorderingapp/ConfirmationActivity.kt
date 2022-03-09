package com.example.pizzaorderingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pizzaorderingapp.databinding.ActivityConfirmationBinding

class ConfirmationActivity : AppCompatActivity() {
    lateinit var binding: ActivityConfirmationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backButton.setOnClickListener {
            finish()
        }
        binding.orderId.text = intent.getIntExtra("id",0).toString()
        binding.orderAmount.text = intent.getIntExtra("orderAmount",0).toString()
    }
}