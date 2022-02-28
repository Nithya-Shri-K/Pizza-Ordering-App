package com.example.pizzaorderingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pizzaorderingapp.databinding.ActivityCustomizeBinding

class CustomizeActivity : AppCompatActivity() {
    lateinit var binding : ActivityCustomizeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomizeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val selectedItem  = intent.getSerializableExtra("selectedItem") as Pizza
        binding.selectedItemName.text = selectedItem.name
        binding.price.text = intent.getStringExtra("selectedPrice")
        binding.size.text = intent.getStringExtra("selectedSize")
        binding.addToCart.setOnClickListener {
            finish()
        }
        val recyclerView = binding.recyclerviewToppings
        val toppings = ToppingAdapter(Database.listOfToppings, USER)
        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        recyclerView.adapter = toppings
    }
}