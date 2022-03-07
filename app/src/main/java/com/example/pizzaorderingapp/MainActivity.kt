package com.example.pizzaorderingapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
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
const val USER_CHOICE = "userChoice"

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    var loggedIn = 0
    lateinit var currentUser : Users
   var cart = arrayListOf<Item>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navigation.selectedItemId = R.id.home

        val getUser = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if(result.resultCode == RESULT_OK){
                val user = result.data?.getSerializableExtra(CURRENT_USER) as Users
                println(user)
                setUserAndNavigateToAccount(user)
            }
        }
        supportFragmentManager.setFragmentResultListener(LOGOUT,this){
                _, bundle ->
            if(bundle.getString(OPERATION) == LOGOUT){
                loggedIn = 0
                cart.clear()
                replaceFragment(UserHomeFragment())
            }
        }
        supportFragmentManager.setFragmentResultListener("login",this){
                _, _ ->
            binding.navigation.selectedItemId = R.id.my_account
        }


        supportFragmentManager.setFragmentResultListener("cartItem",this){
                requestKey,bundle ->

            val item = bundle.getSerializable("item") as Item
            if(loggedIn == 1){
                UserHandler.addToCart(currentUser,item)
                binding.navigation.selectedItemId = R.id.cart
            }
            else{
                cart.add(item)
                binding.navigation.selectedItemId = R.id.cart
            }
        }
        binding.navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home -> replaceFragment(UserHomeFragment())
                R.id.cart -> {
                    val cartFragment = CartFragment()
                    val cartBundle = Bundle()
                    cartBundle.putInt("loggedIn",loggedIn)
                    if(loggedIn == 1)
                        cartBundle.putSerializable("currentUser", currentUser)
                    else
                        cartBundle.putSerializable("cart",cart)
                    cartFragment.arguments = cartBundle
                    replaceFragment(cartFragment)
                }
                R.id.my_account -> {
                    if(loggedIn == 0) {
                        val intent = Intent(this, RegisterOrLoginActivity::class.java)
                        getUser.launch(intent)
                    }
                    else {
                        val fragment = MyAccountFragment()
                        fragment.arguments = bundleOf("user" to currentUser)
                        replaceFragment(fragment)
                    }
                }
            }
            true
        }
    }
    private fun setUserAndNavigateToAccount(user: Users){
        loggedIn = 1
        currentUser =  Database.listOfUsers.filter { it.id == user.id }[0]
        if (currentUser.isAdmin) {
            startActivity(Intent(this, AdminActivity::class.java))
            finish()
        }
        else{
            val fragment = MyAccountFragment()
            fragment.arguments = bundleOf("user" to currentUser)
            replaceFragment(fragment)
            if(cart.isNotEmpty()){
                for(item in cart)
                    currentUser.cart.add(item)
            }
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