package com.vasant.pillpal.data.db

sealed interface MedicineEvent {

    object SaveMedicine : MedicineEvent
    data class DeleteMedicine(val medicine: Medicine) : MedicineEvent
    data class SaveMedicineName(
        val medicineName: String,
        val date: String,
        val dosage: String,
        val note: String? = null,
        val isCompleted: Boolean = false
    ) : MedicineEvent
}