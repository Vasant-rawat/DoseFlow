package com.vasant.pillpal.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Medicine(
    @PrimaryKey(autoGenerate = true)
    val id :Int =0,
    val medName:String,
    val date:String,
    val dosage:String,
    val note: String?=null,
    val isCompleted : Boolean=false
)
