package com.example.pizzaorderingapp

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.pizzaorderingapp.databinding.FragmentMyAccountBinding

class MyAccountFragment : Fragment() {
    lateinit var binding : FragmentMyAccountBinding
    private lateinit var userId : ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userId = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                result : ActivityResult -> if(result.resultCode == Activity.RESULT_OK){
                    println("called result")
            binding.loginRegister.visibility = View.GONE
            binding.myAccount.visibility= View.VISIBLE

        }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyAccountBinding.inflate(layoutInflater,container,false)
        binding.buttonLogin.setOnClickListener {
            val intent = Intent(context,LoginScreen::class.java)
            intent.putExtra("operation","login")
            userId.launch(intent)
        }
        return binding.root
    }


}