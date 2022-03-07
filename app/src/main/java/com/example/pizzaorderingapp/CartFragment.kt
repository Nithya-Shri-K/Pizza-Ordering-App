package com.example.pizzaorderingapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pizzaorderingapp.databinding.FragmentCartBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class CartFragment : Fragment(), CartAdapter.CartActionListener {
    lateinit var binding : FragmentCartBinding
    lateinit var adapterData : CartAdapter
    lateinit var user : Users
    lateinit var cart : ArrayList<Item>

    var loggedIn : Int? = 0
    var totalPrice = 0
    var deliveryAddress = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(layoutInflater, container, false)
        loggedIn = arguments?.getInt("loggedIn")
        if(loggedIn == 1){
            user = arguments?.getSerializable("currentUser") as Users
            val addressBook = user.address
            if(addressBook.isNotEmpty())
                deliveryAddress = user.address[0].completeAddress
            setUserCart()

        }else{
             cart = arguments?.getSerializable("cart") as ArrayList<Item>
            setCart()
        }
        binding.proceedWithLogin.setOnClickListener {
            (activity as MainActivity).binding.navigation.selectedItemId = R.id.my_account
        }
        binding.buttonPlaceOrder.setOnClickListener {
            UserHandler.placeOrder(user.cart,totalPrice,Status.Waiting,user.id,deliveryAddress)

        }
        binding.buttonPlaceOrder.isEnabled = !binding.deliveryAddress.text.isEmpty()
        binding.buttonSelectAddress.setOnClickListener {
            println(user.address)

            val view = layoutInflater.inflate(R.layout.fragment_address_book,null)
            val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview_address_book)
            val dialog = BottomSheetDialog(requireContext())
            val addressBookData = AddressAdapter(user.address,true, object : AddressAdapter.AddressAdapterListener {
                override fun onAddressSelected(address: Address) {
                    deliveryAddress = address.completeAddress
                    binding.deliveryAddress.text = deliveryAddress
                    dialog.dismiss()
                }

            })
            recyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            recyclerView.adapter = addressBookData

           dialog.setContentView(view)
            dialog.show()


//            val intent = Intent(context, UserAccountActivity::class.java)
//            intent.putExtra("operation", "addressBook")
//            intent.putExtra("currentUser", user)
//            intent.putExtra("operation", "selectAddress")
//            startActivity(intent)
        }

        return binding.root
    }
    fun setCart(){
        binding.deliveryAddress.visibility = View.GONE
        binding.labelDeliveryAddress.visibility = View.GONE
        binding.buttonSelectAddress.visibility = View.GONE
        if(cart.isNotEmpty()) {
            binding.loginToContinue.visibility = View.VISIBLE
            binding.buttonPlaceOrder.visibility = View.GONE
            setCartVisibility()
            cartAdapter(cart)
            setCartBillDetails(cart)
        }
        else
            setEmptyCartVisibility()

    }
    fun setUserCart(){
        binding.deliveryAddress.visibility = View.VISIBLE
        binding.labelDeliveryAddress.visibility = View.VISIBLE
        binding.buttonSelectAddress.visibility = View.VISIBLE
        binding.deliveryAddress.text = deliveryAddress
        if(user.cart.isNotEmpty()) {
            binding.loginToContinue.visibility = View.GONE
            binding.buttonPlaceOrder.visibility = View.VISIBLE
            setCartVisibility()
            cartAdapter(user.cart)
            setCartBillDetails(user.cart)
        }
        else
            setEmptyCartVisibility()

    }
    fun setEmptyCartVisibility(){
        binding.emptyCart.visibility = View.VISIBLE
        binding.cart.visibility = View.GONE
    }
    fun setCartVisibility(){
        binding.emptyCart.visibility = View.GONE
        binding.cart.visibility = View.VISIBLE
    }
    private fun setCartBillDetails(cart : ArrayList<Item>){
        var total = 0
        for(i in cart){
            total += i.price
        }
        totalPrice = total + 50
        binding.itemTotalPrice.text = "Rs. ${total.toString()}"
        binding.deliveryFee.text = "Rs. 50"
        binding.total.text = "Rs. ${totalPrice}"
    }
    private fun cartAdapter(cart : ArrayList<Item>){
        val recyclerView = binding.cartRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        adapterData = CartAdapter(cart,this)
        recyclerView.adapter = adapterData
    }

    override fun refresh() {
        adapterData.notifyDataSetChanged()
        if(loggedIn == 1) {
            if(user.cart.isNotEmpty()) {
                setCartVisibility()
                setCartBillDetails(user.cart)
            }
            else
                setEmptyCartVisibility()
        }
        else {
            if(this::cart.isInitialized && cart.isNotEmpty()){
                setCartVisibility()
                setCartBillDetails(cart)
            }
            else
                setEmptyCartVisibility()
        }
    }
}