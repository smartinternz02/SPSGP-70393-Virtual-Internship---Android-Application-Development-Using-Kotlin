package com.tharun.groceryreminder.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tharun.groceryreminder.R
import com.tharun.groceryreminder.databinding.ItemGroceryBinding
import com.tharun.groceryreminder.model.GroceryModel
import com.tharun.groceryreminder.util.ItemDiffUtil

class MainAdapter: RecyclerView.Adapter<MainAdapter.MyViewHolder>() {

    private var grocery = emptyList<GroceryModel>()

    class MyViewHolder(val binding: ItemGroceryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemGroceryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentResult = grocery[position]
        holder.binding.ItemName.text = currentResult.name
        holder.binding.ItemQnt.text = String.format(holder.itemView.context.getString(R.string.item_qtn),currentResult.quantity)
        holder.binding.ItemCost.text = String.format(holder.itemView.context.getString(R.string.item_cost),currentResult.cost)
        holder.binding.Total.text = String.format(holder.itemView.context.getString(R.string.item_total),currentResult.total)
    }

    override fun getItemCount(): Int {
        return grocery.size
    }

    fun setData(newData: List<GroceryModel>) {
        val eventDifUtil = ItemDiffUtil(grocery, newData)
        val diffUtilResult = DiffUtil.calculateDiff(eventDifUtil)
        grocery = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }
}