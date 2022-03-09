package com.example.pizzaorderingapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pizzaorderingapp.databinding.FragmentCartBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class CartFragment : Fragment(), CartAdapter.CartActionListener, addressHandlerListener {
    lateinit var binding: FragmentCartBinding
    lateinit var adapterData: CartAdapter
    lateinit var user: User
    lateinit var cart: ArrayList<Item>
    lateinit var addressBookData: AddressAdapter

    var loggedIn = 0
    var totalPrice = 0
    var deliveryAddress = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(layoutInflater, container, false)
        loggedIn = arguments?.getInt(IS_LOGGED_IN) ?: 0
        if (loggedIn == 1) {
            user = arguments?.getSerializable(CURRENT_USER) as User
            val addressBook = user.address
            if (addressBook.isNotEmpty())
                deliveryAddress = user.address[0].completeAddress
            setUserCart()

        } else {
            cart = arguments?.getSerializable(CART) as ArrayList<Item>
            setCart()
        }
        binding.proceedWithLogin.setOnClickListener {

            (activity as MainActivity).binding.navigation.selectedItemId = R.id.my_account
        }
        binding.buttonPlaceOrder.setOnClickListener {
            val order = UserHandler.placeOrder(
                user.cart,
                totalPrice,
                Status.Waiting,
                user.id,
                deliveryAddress
            )
            val intent = Intent(context, ConfirmationActivity::class.java)
            intent.putExtra(ORDER_ID, order.orderId)
            intent.putExtra(ORDER_AMOUNT, order.totalPrice)
            startActivity(intent)
        }
        binding.buttonPlaceOrder.isEnabled = deliveryAddress.isNotEmpty()
        binding.buttonSelectAddress.setOnClickListener {

            val view = layoutInflater.inflate(R.layout.fragment_address_book, null)
            val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview_address_book)
            val dialog = BottomSheetDialog(requireContext())
            addressBookData =
                AddressAdapter(user.address, true, object : AddressAdapter.AddressAdapterListener {
                    override fun onAddressSelected(address: Address) {
                        deliveryAddress = address.completeAddress
                        binding.deliveryAddress.text = deliveryAddress
                        dialog.dismiss()
                        binding.buttonPlaceOrder.isEnabled = true
                    }

                    override fun refresh() {
                        addressBookData.notifyDataSetChanged()
                    }

                })
            recyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            recyclerView.adapter = addressBookData
            val addAddress = view.findViewById<TextView>(R.id.button_add_address)
            addAddress.setOnClickListener {
                val addAddress = AddressDialogFragment(user, this)
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
        if (cart.isNotEmpty()) {
            binding.loginToContinue.visibility = View.VISIBLE
            binding.buttonPlaceOrder.visibility = View.GONE
            setCartVisibility()
            cartAdapter(cart)
            setCartBillDetails(cart)
        } else
            setEmptyCartVisibility()

    }

    private fun setUserCart() {
        binding.deliveryAddress.visibility = View.VISIBLE
        binding.labelDeliveryAddress.visibility = View.VISIBLE
        binding.buttonSelectAddress.visibility = View.VISIBLE
        if (deliveryAddress.isNotEmpty())
            binding.deliveryAddress.text = deliveryAddress
        else
            binding.deliveryAddress.text = getString(R.string.delivery_address_error)
        if (user.cart.isNotEmpty()) {
            binding.loginToContinue.visibility = View.GONE
            binding.buttonPlaceOrder.visibility = View.VISIBLE
            setCartVisibility()
            cartAdapter(user.cart)
            setCartBillDetails(user.cart)
        } else
            setEmptyCartVisibility()

    }

    private fun setEmptyCartVisibility() {
        binding.emptyCart.visibility = View.VISIBLE
        binding.cart.visibility = View.GONE
    }

    private fun setCartVisibility() {
        binding.emptyCart.visibility = View.GONE
        binding.cart.visibility = View.VISIBLE
    }

    private fun setCartBillDetails(cart: ArrayList<Item>) {
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

    private fun cartAdapter(cart: ArrayList<Item>) {
        val recyclerView = binding.cartRecyclerView
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapterData = CartAdapter(cart, this)
        recyclerView.adapter = adapterData
    }

    override fun refreshData() {
        adapterData.notifyDataSetChanged()
        if (loggedIn == 1) {
            if (user.cart.isNotEmpty()) {
                setCartVisibility()
                setCartBillDetails(user.cart)
            } else
                setEmptyCartVisibility()
        } else {
            if (this::cart.isInitialized && cart.isNotEmpty()) {
                setCartVisibility()
                setCartBillDetails(cart)
            } else
                setEmptyCartVisibility()
        }
    }

    override fun refresh() {

        addressBookData.notifyDataSetChanged()
    }
}