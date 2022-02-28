package com.example.pizzaorderingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.pizzaorderingapp.databinding.ActivityAdminBinding
import com.google.android.material.navigation.NavigationView

class AdminActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAdminBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setFragment(AdminPizzaMenuFragment())
        val drawerLayout : DrawerLayout = binding.drawerLayout
        val navigationView : NavigationView = binding.navigation
        val toolbar = binding.menuToolbar
        val toggle : ActionBarDrawerToggle = ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navigationView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.pizza -> {setFragment(AdminPizzaMenuFragment())
                                drawerLayout.closeDrawers()
                                }
                R.id.topping -> { setFragment(AdminToppingsMenuFragment())
                            drawerLayout.closeDrawers()}
                R.id.logout ->{
                    finish()
                    startActivity(Intent(this,MainActivity::class.java))
                }
            }
            true
        }
    }
    private fun setFragment(fragment : Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.admin_container,fragment).commit()
    }
}