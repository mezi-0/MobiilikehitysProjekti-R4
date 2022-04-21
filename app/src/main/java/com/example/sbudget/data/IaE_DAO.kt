package com.example.sbudget.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface IaE_DAO {

    @Insert
    fun addIaE(data : IaE)


    @Query("SELECT * FROM iae_table WHERE userId = :userId ORDER BY id DESC LIMIT 6")
    fun readSixLasts(userId : String): List<IaE>

    @Query("SELECT * FROM iae_table WHERE userId = :userId ORDER BY id DESC LIMIT 10")
    fun readTenLasts(userId : String): List<IaE>
}