//package com.example.pizzaorderingapp
//
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.pizzaorderingapp.databinding.FragmentToppingsBinding
//
//
//class ToppingsFragment : Fragment(),AdminHandlerListener {
//    lateinit var binding : FragmentToppingsBinding
//    lateinit var toppings : ToppingAdapter
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = FragmentToppingsBinding.inflate(layoutInflater,container,false)
//        val recyclerView = binding.recyclerviewToppings
//        toppings = ToppingAdapter(Database.listOfToppings)
//        recyclerView.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
//        recyclerView.adapter = toppings
//        binding.buttonAddTopping.setOnClickListener {
//            val toppingDialogFragment = ToppingDialogFragment(this)
//            toppingDialogFragment.show(parentFragmentManager,"Topping")
//        }
//
//        return binding.root
//    }
//
//    override fun refresh() {
//        toppings.notifyDataSetChanged()
//    }
//
//    override fun edit(pizza: Items.Pizza) {
//
//    }
//
//
//}