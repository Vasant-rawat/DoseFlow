package com.vasant.pillpal.data.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.vasant.pillpal.data.db.MedicineDatabase
import com.vasant.pillpal.data.db.dao.MedicineDao
import com.vasant.pillpal.repository.Auth
import com.vasant.pillpal.repository.AuthImplementation
import com.vasant.pillpal.repository.MedicineRepo
import com.vasant.pillpal.repository.MedicineRepoImplementation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideMedicineDataBase(@ApplicationContext context: Context): MedicineDatabase {
        return Room.databaseBuilder(
            context = context, MedicineDatabase::class.java,
            name = "medicine_db"
        ).fallbackToDestructiveMigration(true).build();
    }

    @Singleton
    @Provides
    fun provideMedicineDao(db: MedicineDatabase): MedicineDao {
        return db.medicineDao();
    }

    @Singleton
    @Provides
    fun provideMedicineRepo(dao: MedicineDao): MedicineRepo {
        return MedicineRepoImplementation(
            dao = dao
        )
    }
}

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Singleton
    @Provides
    fun provideFirebaseInstance(): FirebaseAuth = FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun provideFirebaseImpl(firebase: FirebaseAuth): Auth {
        return AuthImplementation(firebase)
    }
}