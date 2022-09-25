package com.tharun.groceryreminder.data

import androidx.lifecycle.LiveData
import com.tharun.groceryreminder.data.db.GroceryDao
import com.tharun.groceryreminder.model.GroceryModel

class GroceryRepository(private val groceryDao: GroceryDao) {

    val readAllItems: LiveData<List<GroceryModel>> = groceryDao.readItem()

    suspend fun insert(item: GroceryModel) = groceryDao.insertItem(item)

    suspend fun deleteAllItems() = groceryDao.deleteAllItems()

}