package com.vasant.pillpal.ui.presentation

import com.vasant.pillpal.data.db.Medicine

data class MedicineState(
    val medicines: List<Medicine> = emptyList(),
    val medicineName: String = "",
    val date: String = "",
    val dosage: String = "",
    val note: String? = null,
)