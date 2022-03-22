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

    private lateinit var binding: FragmentMyAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMyAccountBinding.inflate(layoutInflater, container, false)
        val databaseHelper = DatabaseHelper(requireContext())
        val currentUserId = arguments?.getInt(CURRENT_USER_ID)
        val user = currentUserId?.let { databaseHelper.getUser(it) }

        binding.userName.setText(user?.name, TextView.BufferType.EDITABLE)
        binding.userPhoneno.setText(user?.phoneNumber, TextView.BufferType.EDITABLE)
        binding.logout.setOnClickListener {
            setFragmentResult(LOGOUT, bundleOf(OPERATION to LOGOUT))
        }
        binding.orderHistory.setOnClickListener {
            val intent = Intent(context, UserAccountActivity()::class.java)
            intent.putExtra(OPERATION, ORDER_HISTORY)
            intent.putExtra(CURRENT_USER_ID, currentUserId)
            startActivity(intent)
        }
        binding.addressBook.setOnClickListener {
            val intent = Intent(context, UserAccountActivity::class.java)
            intent.putExtra(OPERATION, ADDRESS_BOOK)
            intent.putExtra(CURRENT_USER_ID, currentUserId)
            startActivity(intent)
        }

        return binding.root
    }

}