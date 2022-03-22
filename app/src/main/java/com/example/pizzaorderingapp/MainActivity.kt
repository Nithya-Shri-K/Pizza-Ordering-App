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
const val OPERATION = "operation"
const val LOGIN = "login"
const val REGISTER = "register"
const val LOGOUT = "logout"
const val USER_TYPE_ADMIN = 1
const val USER_TYPE_USER = 0
const val ADMIN = "admin"
const val USER = "user"
const val PIZZA = "pizza"
const val ADD_ITEM = "addItem"
const val EDIT = "edit"
const val USER_CHOICE = "userChoice"
const val SELECTED_ITEM = "selectedItem"
const val SELECTED_SIZE = "selectedSize"
const val IS_LOGGED_IN = "isLoggedIn"
const val ITEM_PRICE = "itemPrice"
const val CART = "cart"
const val ORDER_ID = "orderId"
const val ORDER_AMOUNT = "orderAmount"
const val ORDER_HISTORY = "orderHistory"
const val ADDRESS_BOOK = "addressBook"
const val CURRENT_USER_ID = "userId"
const val SELECTED_PIZZA_ID = "selectedPizzaId"
const val SELECTED_ITEM_ID = "selectedItemId"
const val ADD_ADDRESS = "addAddress"
const val INVALID_ACCOUNT = -1
const val INVALID_PASSWORD = 0


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var databaseHelper: DatabaseHelper
    private var currentUserId: Int = 0
    var isLoggedIn = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        databaseHelper = DatabaseHelper(this)

        val getUser =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val userId = result.data?.getIntExtra(CURRENT_USER_ID, -1)
                    if (userId != -1 && userId != null)
                        setUserAndNavigateToAccount(userId)
                }
            }

        supportFragmentManager.setFragmentResultListener(LOGOUT, this) { _, bundle ->
            if (bundle.getString(OPERATION) == LOGOUT) {
                isLoggedIn = 0
                currentUserId = 0
                replaceFragment(UserHomeFragment())
            }
        }

        supportFragmentManager.setFragmentResultListener(SELECTED_ITEM_ID, this) { _, bundle ->

            val itemId = bundle.getInt(SELECTED_ITEM_ID)
            addItemToCart(itemId, databaseHelper)

        }
        val getSelectedItemFromSearchActivity =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == RESULT_OK) {
                    val itemId = result.data?.getIntExtra(SELECTED_ITEM_ID, 0)
                    if (itemId != null) {
                        addItemToCart(itemId, databaseHelper)
                    }
                    binding.navigation.selectedItemId = R.id.cart
                }
            }

        binding.navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    val fragment = UserHomeFragment()
                    fragment.arguments = bundleOf(CURRENT_USER_ID to currentUserId)
                    replaceFragment(fragment)
                }
                R.id.search -> {
                    val intent = Intent(this, SearchActivity::class.java)
                    getSelectedItemFromSearchActivity.launch(intent)
                }
                R.id.cart -> {
                    val cartFragment = CartFragment()
                    val cartBundle = Bundle()
                    cartBundle.putInt(IS_LOGGED_IN, isLoggedIn)
                    if (isLoggedIn == 1) {
                        cartBundle.putInt(CURRENT_USER_ID, currentUserId)
                    }
                    cartFragment.arguments = cartBundle
                    replaceFragment(cartFragment)
                }
                R.id.my_account -> {
                    if (isLoggedIn == 0) {
                        val intent = Intent(this, RegisterOrLoginActivity::class.java)
                        getUser.launch(intent)
                    } else {
                        val fragment = MyAccountFragment()
                        fragment.arguments = bundleOf(CURRENT_USER_ID to currentUserId)
                        replaceFragment(fragment)
                    }
                }
            }
            true
        }
    }

    override fun onStart() {
        super.onStart()
        binding.navigation.selectedItemId = R.id.home

    }

    private fun addItemToCart(itemId: Int, databaseHelper: DatabaseHelper) {
        UserHandler.addToCart(currentUserId, itemId, databaseHelper)
        binding.navigation.selectedItemId = R.id.cart
    }

    private fun setUserAndNavigateToAccount(userId: Int) {
        isLoggedIn = 1
        currentUserId = userId
        val isAdmin = databaseHelper.getUserType(userId)
        if (isAdmin == 1) {
            startActivity(Intent(this, AdminActivity::class.java))
            finish()
        } else {
            databaseHelper.updateCartUserId(currentUserId)
            val fragment = MyAccountFragment()
            fragment.arguments = bundleOf(CURRENT_USER_ID to currentUserId)
            replaceFragment(fragment)
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment).commit()
    }



}