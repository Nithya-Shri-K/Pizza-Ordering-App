package com.example.pizzaorderingapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.pizzaorderingapp.databinding.ActivityMainBinding
const val PASSWORD_INCORRECT = 0
const val PASSWORD_CORRECT = 1
const val INVALID_USER = 2

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
                if (it.data?.getSerializableExtra("user") != null) {
                    user = it.data?.getSerializableExtra("user") as Users
                    if(user.isAdmin){
                        startActivity(Intent(this,AdminActivity::class.java))
                        finish()
                    }
                    else
                        replaceFragment(MyAccountFragment(user.id))
                }

            }
        }
        supportFragmentManager.setFragmentResultListener("logout",this){
            requestKey,bundle ->
            if(bundle.getString("operation") == "logout"){
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
                        replaceFragment(MyAccountFragment(user.id))

                }
            }
            true
        }


    }
    private fun replaceFragment(fragment : Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container,fragment).commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        println("onDestroy")
    }

    override fun onStart() {
        super.onStart()
        println("onStart")
    }

    override fun onResume() {
        super.onResume()
        println("onResume")
    }
}