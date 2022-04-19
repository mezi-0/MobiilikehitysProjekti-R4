package com.example.sbudget.data

import androidx.room.*

@Database(entities = [IaE::class], version = 1)

abstract class IaE_DATABASE : RoomDatabase() {

    abstract fun iae_dao() : IaE_DAO



}