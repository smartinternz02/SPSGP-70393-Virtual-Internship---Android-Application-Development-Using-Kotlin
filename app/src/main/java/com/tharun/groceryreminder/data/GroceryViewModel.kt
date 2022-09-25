package com.tharun.groceryreminder.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.tharun.groceryreminder.data.db.GroceryDB
import com.tharun.groceryreminder.model.GroceryModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GroceryViewModel(application: Application) : AndroidViewModel(application) {

    val readAllItems: LiveData<List<GroceryModel>>
    private val repository: GroceryRepository

    init {
        val groceryDao = GroceryDB.getDatabase(application).groceryDao()
        repository = GroceryRepository(groceryDao)
        readAllItems = repository.readAllItems
    }

    fun addItems(groceryModel: GroceryModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert((groceryModel))
        }
    }

    fun deleteAllItems() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllItems()
        }
    }
}