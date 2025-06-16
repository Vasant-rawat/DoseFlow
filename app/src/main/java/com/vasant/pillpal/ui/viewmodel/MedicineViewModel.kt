package com.vasant.pillpal.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vasant.pillpal.data.db.Medicine
import com.vasant.pillpal.data.db.MedicineEvent
import com.vasant.pillpal.data.db.dao.MedicineDao
import com.vasant.pillpal.ui.presentation.MedicineState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MedicineViewModel @Inject constructor(
    private val dao: MedicineDao
) : ViewModel() {

    private var medicinesList = dao.getMedicineOrderedByName()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val _state = MutableStateFlow(MedicineState())
    val state = _state

    fun onEvent(event: MedicineEvent) {
        when (event) {
            is MedicineEvent.SaveMedicine -> {
                val medicine = Medicine(
                    medName = state.value.medicineName,
                    date = state.value.date,
                    dosage = state.value.dosage,
                    note = state.value.note
                )
                viewModelScope.launch {
                    dao.upsertMedicine(medicine)
                }
                _state.update {
                    it.copy(
                        medicineName = "",
                        date = "",
                        dosage = "",
                        note = null
                    )
                }
            }

            is MedicineEvent.DeleteMedicine -> {
                viewModelScope.launch {
                    dao.deleteMedicine(event.medicine)
                }
            }

            is MedicineEvent.SaveMedicineName -> {
                val medicine = Medicine(
                    medName = event.medicineName,
                    date = event.date,
                    dosage = event.dosage,
                    note = event.note,
                    isCompleted = event.isCompleted
                )
                viewModelScope.launch {
                }
            }
        }
    }
}