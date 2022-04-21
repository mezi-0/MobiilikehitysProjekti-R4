package com.example.sbudget.data

import androidx.room.*

@Entity(tableName = "iae_table")
data class IaE(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val userId: String,
    val category: String,
    val title: String,
    val cost: String,
    val type: String
)