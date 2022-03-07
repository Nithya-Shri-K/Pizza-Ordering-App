package com.example.pizzaorderingapp

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pizzaorderingapp.databinding.FragmentOrdersBinding
import java.nio.file.attribute.UserDefinedFileAttributeView

import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class OrdersFragment(val userType : String) : Fragment() {
    lateinit var binding : FragmentOrdersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrdersBinding.inflate(layoutInflater,container,false)
        if(userType == "user"){
            val currentUser = arguments?.getSerializable("user") as Users
            val recyclerView = binding.listOfOrders
            recyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,true)
            val orders = Database.listOfOrders.filter { it.userId == currentUser.id } as ArrayList<Order>
            recyclerView.adapter = OrdersAdapter(orders,"user")
            binding.selectDate.visibility = View.GONE
        }
        else {
            val myFormat = "dd-MM-yyyy"
            val formattedDate = SimpleDateFormat(myFormat, Locale.UK)
            val myCalender = Calendar.getInstance()
            val recyclerView = binding.listOfOrders
            filterOrders(myCalender.time, recyclerView)
            val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                myCalender.set(Calendar.YEAR, year)
                myCalender.set(Calendar.MONTH, month)
                myCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateText(myCalender)
                filterOrders(myCalender.time, recyclerView)

            }


            recyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true)
            //recyclerView.adapter = OrdersAdapter(Database.listOfOrders,"admin")

            binding.selectDate.setText(
                formattedDate.format(myCalender.time),
                TextView.BufferType.EDITABLE
            )
            binding.selectDate.setOnClickListener {
                DatePickerDialog(
                    requireContext(),
                    datePicker,
                    myCalender.get(Calendar.YEAR),
                    myCalender.get(Calendar.MONTH),
                    myCalender.get(Calendar.DAY_OF_MONTH)
                ).show()

            }
        }



        return binding.root
    }
    fun filterOrders(date : Date,recyclerView: RecyclerView){

        val myFormat = "dd-MM-yyyy"
        val formattedDate = SimpleDateFormat(myFormat,Locale.UK)
        val selectedDate = formattedDate.format(date)
        println(selectedDate)
        val orders = Database.listOfOrders.filter {
            it.date.compareTo(selectedDate) == 0 } as ArrayList<Order>
        println(orders)
        recyclerView.adapter = OrdersAdapter(orders,"admin")
    }
    fun updateText(myCalender: Calendar){
        val myFormat = "dd-MM-yyyy"
        println("updateText")
        val formattedDate = SimpleDateFormat(myFormat,Locale.UK)
        binding.selectDate.setText(formattedDate.format(myCalender.time), TextView.BufferType.EDITABLE)

    }
}