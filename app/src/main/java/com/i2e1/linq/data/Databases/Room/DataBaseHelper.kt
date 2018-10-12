package com.i2e1.linq.data.Databases.Room

import android.arch.persistence.room.Room
import android.content.Context
import com.grampower.menuapplication.data.database.AppRoomDatabase
import com.i2e1.linq.data.Pojo.PersonWrapper


class DataBaseHelper constructor( private val context: Context ) : dbHelper {



   private var  appRoomDatabase:AppRoomDatabase
    init {
       appRoomDatabase= Room.databaseBuilder(context, AppRoomDatabase::class.java, "linqdb").allowMainThreadQueries().build()
    }


    override fun insertPerson(person: PersonWrapper?): Long =appRoomDatabase.personDao().insertPersons(person)

    override fun insertPersonList(personList: MutableList<PersonWrapper>): MutableList<Long> =appRoomDatabase.personDao().insertPersonsList(personList)

    override fun updateImageData(imageUrl: String, imageData: String): Long =appRoomDatabase.personDao().updateImageData(imageUrl,imageData)

    override fun getPersons(): MutableList<PersonWrapper> = appRoomDatabase.personDao().getPersonsFromDB()



}