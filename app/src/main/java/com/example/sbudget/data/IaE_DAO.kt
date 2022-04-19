package com.example.sbudget.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface IaE_DAO {

    @Insert
    fun addIaE(data : IaE)


    @Query("SELECT * FROM iae_table ORDER BY id DESC LIMIT :from, :to")
    fun readSixLasts(from: Int, to: Int): List<IaE>
}