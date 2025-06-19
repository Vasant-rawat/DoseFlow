package com.vasant.pillpal.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vasant.pillpal.data.db.Medicine
import com.vasant.pillpal.data.db.MedicineEvent
import com.vasant.pillpal.repository.MedicineRepo
import com.vasant.pillpal.ui.presentation.MedicineState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MedicineViewModel @Inject constructor(
    private val medicineRepo: MedicineRepo
) : ViewModel() {
    private val _state = MutableStateFlow(MedicineState())
    val state = _state
    private val _medicine = medicineRepo.getMedicine()
    val meds: StateFlow<List<Medicine>> =
        _medicine.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun onEvent(event: MedicineEvent) {
        when (event) {
            is MedicineEvent.SaveMedicine -> {
                val medicine = Medicine(
                    medName = _state.value.medicineName,
                    date = _state.value.date,
                    dosage = _state.value.dosage,
                    note = _state.value.note
                )
                viewModelScope.launch {
                    medicineRepo.addMedicine(medicine = medicine)
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
                    medicineRepo.deleteMedicine(event.medicine)
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
                    medicineRepo.addMedicine(medicine = medicine)
                }
            }

            is MedicineEvent.AddDosageChange -> {
                _state.update {
                    it.copy(
                        dosage = event.dosage
                    )
                }
                Log.d("Viewmodel", "onEvent: Dosage Add  = ${_state.value}")
            }

            is MedicineEvent.DateChanged -> {
                _state.update {
                    it.copy(date = event.date)
                }

                Log.d("Viewmodel", "onEvent: it.date = ${_state.value}")
            }

            is MedicineEvent.MedicineNameChanged -> {
                _state.update {
                    it.copy(medicineName =  event.medicineName)
                }
                Log.d("Viewmodel", "onEvent: medicineName change = ${_state.value}")
            }
        }
    }
}