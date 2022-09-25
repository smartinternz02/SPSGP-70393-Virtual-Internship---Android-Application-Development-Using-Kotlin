package com.tharun.groceryreminder.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tharun.groceryreminder.model.GroceryModel
import com.tharun.groceryreminder.util.Constants

@Database(
    entities = [GroceryModel::class],
    version = 1,
    exportSchema = false
)
abstract class GroceryDB: RoomDatabase() {

    abstract fun groceryDao(): GroceryDao

    companion object{
        @Volatile
        private var INSTANCE: GroceryDB? = null

        fun getDatabase(context: Context): GroceryDB{
            val temInstance = INSTANCE
            if (temInstance!=null) {
                return temInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GroceryDB::class.java,
                    Constants.GROCERY_NAME
                ).build()
                INSTANCE = instance
                return instance
            }

        }
    }
}