package com.example.pizzaorderingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResult
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
const val CART_ITEM = "cartItem"
const val SELECTED_ITEM = "selectedItem"
const val SELECTED_SIZE = "selectedSize"
const val IS_LOGGED_IN = "isLoggedIn"
const val ITEM_PRICE = "itemPrice"
const val CART = "cart"
const val ORDER_ID = "orderId"
const val ORDER_AMOUNT = "orderAmount"


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var currentUser: User
    var isLoggedIn = 0
    var cart = arrayListOf<Item>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val getUser =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val user = result.data?.getSerializableExtra(CURRENT_USER) as User
                    setUserAndNavigateToAccount(user)
                }
            }
        supportFragmentManager.setFragmentResultListener(LOGOUT, this) { _, bundle ->
            if (bundle.getString(OPERATION) == LOGOUT) {
                isLoggedIn = 0
                cart.clear()
                replaceFragment(UserHomeFragment())
            }
        }

        supportFragmentManager.setFragmentResultListener(SELECTED_ITEM, this) { _, bundle ->

            val item = bundle.getSerializable(SELECTED_ITEM) as Item
            addItemToCart(item)

        }
        val getSelectedItemFromSearchActivity =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == RESULT_OK) {
                    val item = result.data?.getSerializableExtra(SELECTED_ITEM) as Item
                    addItemToCart(item)
                }
            }

        binding.navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(UserHomeFragment())
                R.id.search -> {
                    val intent = Intent(this, SearchActivity::class.java)
                    getSelectedItemFromSearchActivity.launch(intent)
                }
                R.id.cart -> {
                    val cartFragment = CartFragment()
                    val cartBundle = Bundle()
                    cartBundle.putInt(IS_LOGGED_IN, isLoggedIn)
                    if (isLoggedIn == 1)
                        cartBundle.putSerializable(CURRENT_USER, currentUser)
                    else
                        cartBundle.putSerializable(CART, cart)
                    cartFragment.arguments = cartBundle
                    replaceFragment(cartFragment)
                }
                R.id.my_account -> {
                    if (isLoggedIn == 0) {
                        val intent = Intent(this, RegisterOrLoginActivity::class.java)
                        getUser.launch(intent)
                    } else {
                        val fragment = MyAccountFragment()
                        fragment.arguments = bundleOf(CURRENT_USER to currentUser)
                        replaceFragment(fragment)
                    }
                }
            }
            true
        }
    }

    private fun addItemToCart(item: Item) {
        if (isLoggedIn == 1) {
            UserHandler.addToCart(currentUser, item)
            binding.navigation.selectedItemId = R.id.cart
        } else {
            cart.add(item)
            binding.navigation.selectedItemId = R.id.cart
        }
    }

    private fun setUserAndNavigateToAccount(user: User) {
        isLoggedIn = 1
        currentUser = Database.listOfUsers.filter { it.id == user.id }[0]
        if (currentUser.isAdmin) {
            startActivity(Intent(this, AdminActivity::class.java))
            finish()
        } else {
            if (cart.isNotEmpty()) {
                for (item in cart)
                    currentUser.cart.add(item)
            }
            val fragment = MyAccountFragment()
            fragment.arguments = bundleOf(CURRENT_USER to currentUser)
            replaceFragment(fragment)
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment).commit()
    }

    override fun onStart() {
        super.onStart()
        binding.navigation.selectedItemId = R.id.home
    }


}