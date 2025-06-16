package com.vasant.pillpal.repository

interface MedicineRepo {
    suspend fun addMedicine()
    suspend fun deleteMedicine()
}