package com.vasant.pillpal.repository

import com.vasant.pillpal.data.db.Medicine
import com.vasant.pillpal.data.db.dao.MedicineDao
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class MedicineRepoImplementation @Inject constructor(
    private val dao : MedicineDao
) : MedicineRepo {
    override suspend fun addMedicine(medicine: Medicine) {
        dao.upsertMedicine(medicine)
    }

    override suspend fun updateMedicine(medicine: Medicine) {
        dao.upsertMedicine(medicine)
    }

    override fun getMedicine(): Flow<List<Medicine>> {
     return  dao.getMedicineOrderedByName()
    }
    override suspend fun deleteMedicine(medicine: Medicine) {
        dao.deleteMedicine(medicine =medicine )
    }
}