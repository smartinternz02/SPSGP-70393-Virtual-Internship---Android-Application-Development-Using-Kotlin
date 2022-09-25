package com.tharun.groceryreminder

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.tharun.groceryreminder.data.GroceryViewModel
import com.tharun.groceryreminder.databinding.FragmentCreateBinding
import com.tharun.groceryreminder.model.GroceryModel

class CreateFragment : Fragment() {

    private lateinit var binding: FragmentCreateBinding
    private lateinit var viewModel: GroceryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[GroceryViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCreateBinding.inflate(inflater, container, false)

        binding.Upload.setOnClickListener {
            insertDataToDatabase()
        }

        return binding.root
    }

    private fun insertDataToDatabase() {
        val groceryNm =  binding.name.text.toString()
        val groceryQtn = binding.quantity.text.toString()
        val groceryCost = binding.cost.text.toString()

        if (inputCheck(groceryNm,groceryQtn,groceryCost)) {
            val total = Integer.parseInt(groceryQtn)*Integer.parseInt(groceryCost)
            val grocery = GroceryModel(groceryNm,Integer.parseInt(groceryQtn),Integer.parseInt(groceryCost), total)
            viewModel.addItems(grocery)
            Toast.makeText(requireContext(),"Successfully added to database",Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_createFragment_to_homeFragment)
        } else {
            Toast.makeText(requireContext(),"Please fill all the fields",Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(groceryNm: String, groceryQtn: String, groceryCost: String): Boolean {
        return !(TextUtils.isEmpty(groceryNm) && TextUtils.isEmpty(groceryQtn) && TextUtils.isEmpty(groceryCost))
    }

}