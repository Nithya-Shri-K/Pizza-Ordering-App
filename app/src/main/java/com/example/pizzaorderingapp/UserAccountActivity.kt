package com.example.pizzaorderingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.pizzaorderingapp.databinding.ActivityUserAccountBinding

class UserAccountActivity : AppCompatActivity() {
    lateinit var binding: ActivityUserAccountBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val operation = intent.getStringExtra(OPERATION)
        val currentUserId = intent.getIntExtra(CURRENT_USER_ID,0)
        when (operation) {
            ORDER_HISTORY -> {
                val fragment = OrdersFragment(USER)
                fragment.arguments = bundleOf(CURRENT_USER_ID to currentUserId)
                replaceFragment(fragment)
            }
            ADDRESS_BOOK -> {
                val fragment = AddressBookFragment()
                fragment.arguments = bundleOf(CURRENT_USER_ID to currentUserId)
                replaceFragment(fragment)
            }
        }
        binding.backButton.setOnClickListener {
            finish()
        }
    }


    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.account_fragment_container, fragment).commit()
    }
}