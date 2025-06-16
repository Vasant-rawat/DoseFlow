package com.vasant.pillpal.data.di

import android.content.Context
import androidx.room.Room
import com.vasant.pillpal.data.db.MedicineDatabase
import com.vasant.pillpal.data.db.dao.MedicineDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(Singleton::class)
object AppModule {
    @Singleton
    @Provides
    fun provideDao(@ApplicationContext context: Context) : MedicineDatabase{
        return Room.databaseBuilder(
            context =context
            ,MedicineDatabase::class.java,
            name = "medicine_db"
        ).build();
    }
    @Singleton
    @Provides
    fun provideMedicineDao(db: MedicineDatabase) : MedicineDao{
        return db.medicineDao();
    }
}