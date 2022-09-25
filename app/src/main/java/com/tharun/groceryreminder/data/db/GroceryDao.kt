package com.tharun.groceryreminder.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tharun.groceryreminder.model.GroceryModel

@Dao
interface GroceryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(items: GroceryModel)

    @Query("SELECT * FROM grocery_table")
    fun readItem(): LiveData<List<GroceryModel>>

    @Query("DELETE FROM grocery_table")
    suspend fun deleteAllItems()

}