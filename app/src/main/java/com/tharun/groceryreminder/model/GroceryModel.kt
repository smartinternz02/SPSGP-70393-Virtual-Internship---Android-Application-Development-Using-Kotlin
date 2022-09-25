package com.tharun.groceryreminder.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tharun.groceryreminder.util.Constants

@Entity(tableName = Constants.GROCERY_TABLE)
data class GroceryModel(
    @ColumnInfo(name = "ItemName")
    val name: String,
    @ColumnInfo(name = "ItemQty")
    val quantity: Int,
    @ColumnInfo(name = "ItemCost")
    val cost: Int,
    @ColumnInfo(name = "totalPrice")
    val total: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}