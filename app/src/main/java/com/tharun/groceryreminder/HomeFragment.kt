package com.tharun.groceryreminder

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tharun.groceryreminder.adapter.MainAdapter
import com.tharun.groceryreminder.data.GroceryViewModel
import com.tharun.groceryreminder.databinding.FragmentHomeBinding


class HomeFragment : Fragment(), MenuProvider {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: GroceryViewModel
    private val myAdapter by lazy { MainAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[GroceryViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        setUpRecyclerview()

        lifecycleScope.launchWhenCreated {
            viewModel.readAllItems.observe(viewLifecycleOwner) { items ->
                if (items.isNotEmpty()) {
                    myAdapter.setData(items)
                    binding.emptyState.visibility = View.GONE
                } else {
                    myAdapter.setData(items)
                    binding.emptyState.visibility = View.VISIBLE
                }
                hideShimmerEffect()
            }
        }

        binding.addGrocery.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_createFragment)
        }

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this,viewLifecycleOwner)

        return binding.root
    }



    private fun setUpRecyclerview() {
        binding.rvItems.apply {
            adapter = myAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }

        showShimmerEffect()
    }

    private fun showShimmerEffect() {
        binding.rvProgress.visibility = View.VISIBLE
        binding.rvItems.visibility = View.GONE
    }

    private fun hideShimmerEffect() {
        binding.rvProgress.visibility = View.GONE
        binding.rvItems.visibility = View.VISIBLE
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu,menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId) {
            R.id.delete -> {
                deleteAllItems()
                true
            }
            else -> false
        }
    }

    private fun deleteAllItems() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Delete Alert")
            .setMessage("Do you want to delete all your Grocery Items")
            .setNegativeButton("cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton("yes") { _, _ ->
                deleteAction()
            }
            .show()
    }

    private fun deleteAction() {
        lifecycleScope.launchWhenCreated {
            viewModel.deleteAllItems()
            Toast.makeText(requireContext(),"All the grocery items deleted successfully",Toast.LENGTH_LONG).show()
        }
    }
}