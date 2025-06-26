package com.vasant.pillpal.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vasant.pillpal.data.db.dao.MedicineDao

@Database(
    entities = [Medicine::class], version = 2, exportSchema = true
)
abstract class MedicineDatabase : RoomDatabase() {
    abstract fun medicineDao(): MedicineDao
}