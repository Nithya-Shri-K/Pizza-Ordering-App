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
        val user = intent.getSerializableExtra(CURRENT_USER) as User
        val currentUser = Database.listOfUsers.filter {
            it.id == user.id
        }[0]
        when (operation) {
            ORDER_HISTORY -> {
                val fragment = OrdersFragment(USER)
                fragment.arguments = bundleOf(CURRENT_USER to currentUser)
                replaceFragment(fragment)
            }
            ADDRESS_BOOK -> {
                val fragment = AddressBookFragment()
                fragment.arguments = bundleOf(CURRENT_USER to currentUser)
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