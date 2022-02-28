package com.example.pizzaorderingapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.pizzaorderingapp.databinding.ActivityMainBinding
const val CURRENT_USER = "currentUser"
const val CURRENT_USER_KEY = "currentUserKey"
const val REQUEST_KEY = "requestKey"
const val OPERATION = "operation"
const val LOGIN = "login"
const val REGISTER = "register"
const val LOGOUT = "logout"
const val ADMIN = 0
const val USER = 1
const val PIZZA = "pizza"
const val ADD_ITEM = "addItem"
const val EDIT = "edit"

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    var loggedIn = 0
   lateinit var user : Users
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(UserHomeFragment())
        val getUser = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == Activity.RESULT_OK) {
                loggedIn = 1
                if (it.data?.getSerializableExtra(CURRENT_USER) != null) {
                    user = it.data?.getSerializableExtra(CURRENT_USER) as Users
                    if (user.isAdmin) {
                        startActivity(Intent(this, AdminActivity::class.java))
                        finish()
                    } else {
                        replaceFragment(MyAccountFragment(user))
                    }
                }
            }
        }
        supportFragmentManager.setFragmentResultListener(LOGOUT,this){
            requestKey,bundle ->
            if(bundle.getString(OPERATION) == LOGOUT){
                loggedIn = 0
                replaceFragment(UserHomeFragment())

            }
        }
        binding.navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home -> replaceFragment(UserHomeFragment())
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

    override fun onStart() {
        super.onStart()
        binding.navigation.selectedItemId = R.id.home
    }

    private fun replaceFragment(fragment : Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container,fragment).commit()
    }

}