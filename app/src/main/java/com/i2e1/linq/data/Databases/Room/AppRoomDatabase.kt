package com.grampower.menuapplication.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.i2e1.linq.data.Databases.Room.PersonsDao
import com.i2e1.linq.data.Pojo.PersonWrapper

@Database(entities = [PersonWrapper::class], version = 1, exportSchema = false)
abstract class AppRoomDatabase : RoomDatabase() {

    abstract  fun personDao() : PersonsDao

}