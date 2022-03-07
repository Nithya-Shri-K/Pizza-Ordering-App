package com.example.pizzaorderingapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.example.pizzaorderingapp.databinding.FragmentMyAccountBinding


class MyAccountFragment : Fragment() {

    private lateinit var binding : FragmentMyAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val currentUser = arguments?.getSerializable("user") as Users

        binding = FragmentMyAccountBinding.inflate(layoutInflater,container,false)
        binding.userName.setText(currentUser?.name, TextView.BufferType.EDITABLE)
        binding.userPhoneno.setText(currentUser?.phoneNumber, TextView.BufferType.EDITABLE)
        binding.logout.setOnClickListener {
        setFragmentResult(LOGOUT, bundleOf(OPERATION to LOGOUT))
        }
        binding.orderHistory.setOnClickListener {
            val intent = Intent(context,UserAccountActivity()::class.java)
            intent.putExtra("operation","orderHistory")
            intent.putExtra("currentUser",currentUser)
            startActivity(intent)
        }
        binding.addressBook.setOnClickListener {
            val intent = Intent(context,UserAccountActivity::class.java)
            intent.putExtra("operation","addressBook")
            println("fgtyhju - 1")
            intent.putExtra("currentUser",currentUser)
            println("fgtyhju - $currentUser")
            startActivity(intent)
            println("fgtyhju - 3")
        }

       return binding.root
    }

}