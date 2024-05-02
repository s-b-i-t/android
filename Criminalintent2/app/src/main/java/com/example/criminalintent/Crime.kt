package com.example.criminalintent

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID
data class Crime(
     val id : UUID,
    val title : String,
    val date : Date,
    val isSolved : Boolean
)
