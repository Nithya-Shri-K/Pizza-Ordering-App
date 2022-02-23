package com.example.pizzaorderingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.pizzaorderingapp.databinding.ActivityAdminBinding

class AdminActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAdminBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setFragment(AdminMenuFragment())

        binding.adminNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menu -> setFragment(AdminMenuFragment())
            }
            true
        }
        binding.adminNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.my_account -> setFragment(AdminMyAccountFragment())
            }
            true
        }
    }
    private fun setFragment(fragment : Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.admin_container,fragment).commit()
    }
}