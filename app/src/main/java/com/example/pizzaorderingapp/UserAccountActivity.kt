package com.example.pizzaorderingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.pizzaorderingapp.databinding.ActivityUserAccountBinding

class UserAccountActivity : AppCompatActivity() {
    lateinit var binding : ActivityUserAccountBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val operation = intent.getStringExtra("operation")
        val user = intent.getSerializableExtra("currentUser") as Users
        val currentUser = Database.listOfUsers.filter{
            it.id == user.id
        }[0]
        when(operation)
        {
            "orderHistory" -> {
                val fragment = OrdersFragment("user")
                fragment.arguments = bundleOf("user" to currentUser )
                replaceFragment(fragment)
            }
            "addressBook" -> {
                val fragment = AddressBookFragment()
                fragment.arguments = bundleOf("user" to currentUser)
                replaceFragment(fragment)
            }
        }
    }
    private fun replaceFragment(fragment : Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.account_fragment_container,fragment).commit()
    }
}