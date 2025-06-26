package com.vasant.pillpal.ui.presentation

import com.vasant.pillpal.data.db.Medicine

data class MedicineState(
    val medicines: List<Medicine> = emptyList(),
    val isLoading: Boolean=false,
    val medicineName: String = "",
    val isCompleted:Boolean =false,
    val date:Long =0,
    val dosage: String = "",
    val note: String? = null,
)