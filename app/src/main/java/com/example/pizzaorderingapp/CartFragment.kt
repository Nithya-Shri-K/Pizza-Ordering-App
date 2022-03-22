package com.example.pizzaorderingapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pizzaorderingapp.databinding.FragmentAddressBookBinding
import com.example.pizzaorderingapp.databinding.FragmentCartBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class CartFragment : Fragment(),CartActionListener {
    lateinit var binding: FragmentCartBinding
    lateinit var cartAdapterData: CartAdapter

    lateinit var cartItemsList: MutableList<Item>
    lateinit var addressBookData: AddressAdapter
    lateinit var databaseHelper: DatabaseHelper

    var loggedIn = 0
    var totalPrice = 0
    var deliveryAddress = ""
    var deliveryAddressId = 0
    var userId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(layoutInflater, container, false)
        databaseHelper = DatabaseHelper(requireContext())
        loggedIn = arguments?.getInt(IS_LOGGED_IN) ?: 0
        if (loggedIn == 1) {
            userId = arguments?.getInt(CURRENT_USER_ID) ?: -1
            if(userId != -1)
                setUserCart()

        } else {
            setCart()
        }
        binding.proceedWithLogin.setOnClickListener {

            (activity as MainActivity).binding.navigation.selectedItemId = R.id.my_account
        }
        binding.buttonPlaceOrder.setOnClickListener {

            val orderId = UserHandler.placeOrder(
                userId,
                totalPrice,
                Status.Waiting,
                deliveryAddressId,
                databaseHelper
            )
            val intent = Intent(context, ConfirmationActivity::class.java)
            intent.putExtra(ORDER_ID, orderId)
            startActivity(intent)

        }
        binding.buttonPlaceOrder.isEnabled = deliveryAddress.isNotEmpty()
        binding.buttonSelectAddress.setOnClickListener {

            val view = layoutInflater.inflate(R.layout.fragment_address_book, null)
            val addressBinding = FragmentAddressBookBinding.inflate(LayoutInflater.from(context))
            val recyclerView = addressBinding.recyclerviewAddressBook
            val dialog = BottomSheetDialog(requireContext())
            addressBookData =
                AddressAdapter(
                    databaseHelper.getUserAddress(userId),
                    true,
                    object : AddressSelectorListener {
                        override fun onAddressSelected(address: Address) {
                            deliveryAddress = address.completeAddress
                            binding.deliveryAddress.text = deliveryAddress
                            deliveryAddressId = address.addressId
                            dialog.dismiss()
                            binding.buttonPlaceOrder.isEnabled = true
                        }

                    }, object : AddressHandlerListener {
                        override fun refreshAddressList() {
                            addressBookData.addressBook = databaseHelper.getUserAddress(userId)
                            addressBookData.notifyDataSetChanged()
                        }
                    }, requireContext()
                )
            recyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            recyclerView.adapter = addressBookData
            val addAddress = view.findViewById<TextView>(R.id.button_add_address)
            addAddress.setOnClickListener {
                val addAddress = AddressDialogFragment(userId, object : AddressHandlerListener {

                    override fun refreshAddressList() {
                        addressBookData.addressBook = databaseHelper.getUserAddress(userId)
                        addressBookData.notifyDataSetChanged()
                    }
                })
                addAddress.show(parentFragmentManager, "add Address")
            }
            dialog.setContentView(view)
            dialog.show()

        }

        return binding.root
    }

    fun setCart() {

        binding.deliveryAddress.visibility = View.GONE
        binding.labelDeliveryAddress.visibility = View.GONE
        binding.buttonSelectAddress.visibility = View.GONE
        val listOfItems = databaseHelper.getCartItems(userId)
        if (listOfItems.isNotEmpty()) {
            binding.loginToContinue.visibility = View.VISIBLE
            binding.buttonPlaceOrder.visibility = View.GONE
            setCartVisibility()
            setCartAdapter(listOfItems)
            setCartBillDetails(listOfItems)
        } else
            setEmptyCartVisibility()

    }

    private fun setUserCart() {

        val listOfItems = databaseHelper.getCartItems(userId)
        if (listOfItems.isNotEmpty()) {

            binding.loginToContinue.visibility = View.GONE
            binding.buttonPlaceOrder.visibility = View.VISIBLE
            setCartVisibility()
            setCartAdapter(listOfItems)
            setCartBillDetails(listOfItems)
        } else
            setEmptyCartVisibility()

        val userAddress = databaseHelper.getUserAddress(userId)
        if (userAddress.count() > 0)
            deliveryAddress = userAddress[0].completeAddress

        binding.deliveryAddress.visibility = View.VISIBLE
        binding.labelDeliveryAddress.visibility = View.VISIBLE
        binding.buttonSelectAddress.visibility = View.VISIBLE
        if (deliveryAddress.isNotEmpty())
            binding.deliveryAddress.text = deliveryAddress
        else
            binding.deliveryAddress.text = getString(R.string.delivery_address_error)

    }

    private fun setEmptyCartVisibility() {
        binding.emptyCart.visibility = View.VISIBLE
        binding.cart.visibility = View.GONE
    }

    private fun setCartVisibility() {
        binding.emptyCart.visibility = View.GONE
        binding.cart.visibility = View.VISIBLE
    }

    private fun setCartBillDetails(cart: MutableList<Item>) {
        var total = 0
        var deliveryFee = 50
        for (item in cart) {
            total += item.price
        }
        totalPrice = total + deliveryFee
        binding.itemTotalPrice.text = getString(R.string.price_prefix, total.toString())
        binding.deliveryFee.text = getString(R.string.price_prefix, deliveryFee.toString())
        binding.total.text = getString(R.string.price_prefix, totalPrice.toString())
    }

    private fun setCartAdapter(listOfItems: MutableList<Item>) {


        val recyclerView = binding.cartRecyclerView
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        cartAdapterData = CartAdapter(listOfItems, this, requireContext())
        recyclerView.adapter = cartAdapterData
    }

    override fun refreshCart() {
        cartAdapterData.notifyDataSetChanged()
        if (loggedIn == 1) {
            val cartItems = databaseHelper.getCartItems(userId)
            val cartItemsCount = cartItems.count()
            if (cartItemsCount > 0) {
                setCartVisibility()
                setCartBillDetails(cartItems)
            } else
                setEmptyCartVisibility()
        } else {
            if (this::cartItemsList.isInitialized && cartItemsList.isNotEmpty()) {
                setCartVisibility()
                setCartBillDetails(cartItemsList)
            } else
                setEmptyCartVisibility()
        }
    }
}