package com.example.pizzaorderingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pizzaorderingapp.databinding.ActivityConfirmationBinding

class ConfirmationActivity : AppCompatActivity() {
    lateinit var binding: ActivityConfirmationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmationBinding.inflate(layoutInflater)
        val databaseHelper = DatabaseHelper(this)
        setContentView(binding.root)
        binding.backButton.setOnClickListener {
            finish()
        }
        val orderId = intent.getIntExtra(ORDER_ID, 0)
        val orderAmount = databaseHelper.getOrderTotalAmount(orderId)
        binding.orderId.text = orderId.toString()
        binding.orderAmount.text =
            getString(R.string.price_prefix, orderAmount.toString())
    }
}