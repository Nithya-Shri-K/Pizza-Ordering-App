package com.example.pizzaorderingapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.pizzaorderingapp.databinding.ActivityMainBinding
const val USER = "user"
const val OPERATION = "operation"
const val LOGOUT = "logout"

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    var loggedIn = 0
   lateinit var user : Users
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())
        val getUser = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == Activity.RESULT_OK) {
                loggedIn = 1
                if (it.data?.getSerializableExtra(USER) != null) {
                    user = it.data?.getSerializableExtra(USER) as Users
                    if (user.isAdmin) {
                        startActivity(Intent(this, AdminActivity::class.java))
                        finish()
                    } else {
                        println(user.id)
                        replaceFragment(MyAccountFragment(user))
                    }
                }
            }
        }
        supportFragmentManager.setFragmentResultListener(LOGOUT,this){
            requestKey,bundle ->
            if(bundle.getString(OPERATION) == LOGOUT){
                loggedIn = 0
                replaceFragment(HomeFragment())

            }
        }
        binding.navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home -> replaceFragment(HomeFragment())
                R.id.my_account -> {
                    if(loggedIn == 0) {
                        val intent = Intent(this, RegisterOrLoginActivity::class.java)
                        getUser.launch(intent)
                    }
                    else
                        replaceFragment(MyAccountFragment(user))

                }
            }
            true
        }


    }
    private fun replaceFragment(fragment : Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container,fragment).commit()
    }

}