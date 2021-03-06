package com.example.pizzaorderingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pizzaorderingapp.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {
    lateinit var binding: ActivitySearchBinding
    var databaseHelper = DatabaseHelper(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val recyclerView = binding.recyclerViewSearch
        val searchItems = arrayListOf<Pizza>()
        val getItem =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == RESULT_OK) {
                    val itemId = result.data?.getIntExtra(SELECTED_ITEM_ID,0)
                    intent.putExtra(SELECTED_ITEM_ID, itemId)
                    setResult(RESULT_OK, intent)
                    finish()
                }

            }
        val adapterData = MenuAdapter(databaseHelper.getPizza(), this, object : UserActionListener {

            override fun startCustomizeFragment(selectedItemId: Int, size: Size, price: Int) {
                val intent = Intent(applicationContext, CustomizeActivity::class.java)
                intent.putExtra(SELECTED_PIZZA_ID, selectedItemId)
                intent.putExtra(SELECTED_SIZE, size)
                intent.putExtra(ITEM_PRICE, price)
                getItem.launch(intent)
            }
        })

        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapterData


        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String?): Boolean {
                searchItems.clear()
                val searchText = text!!.toString().lowercase()
                filterItems(searchText, adapterData, searchItems)
                return false
            }

            override fun onQueryTextChange(text: String?): Boolean {
                searchItems.clear()
                val searchText = text!!.toString().lowercase()
                filterItems(searchText, adapterData, searchItems)
                return false
            }

        })

        binding.backButton.setOnClickListener {
            finish()
        }

    }

    fun filterItems(searchText: String, adapterData: MenuAdapter, searchItems: ArrayList<Pizza>) {

        if (searchText.isNotEmpty()) {
            val listOfItems = databaseHelper.getPizza()
            listOfItems.forEach {
                if (it.name.lowercase().contains(searchText)) {
                    searchItems.add(it)
                }
            }
            adapterData.menu = searchItems
            adapterData.notifyDataSetChanged()
        } else {
            adapterData.menu = databaseHelper.getPizza()
            adapterData.notifyDataSetChanged()
        }
    }
}