package com.i2e1.linq.data.Databases.Room

import android.arch.persistence.room.*
import com.i2e1.linq.data.Pojo.PersonWrapper

@Dao
interface PersonsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPersons(person: PersonWrapper?): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPersonsList(person: MutableList<PersonWrapper>): MutableList<Long>

    @Query("SELECT * FROM tab_persons")
    fun getPersonsFromDB(): MutableList<PersonWrapper>

    @Query("UPDATE tab_persons SET pictureImageData = :imageData where pictureUrl=:imageUrl")
    fun updateImageData(imageUrl:String,imageData :String):Long

    @Delete
    fun deletePerson(person: PersonWrapper)
}