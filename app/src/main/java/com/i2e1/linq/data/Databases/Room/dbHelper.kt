package com.i2e1.linq.data.Databases.Room

import com.i2e1.linq.data.Pojo.PersonWrapper


interface dbHelper {

    fun insertPerson(person : PersonWrapper?):Long

    fun insertPersonList(personList : MutableList<PersonWrapper>):MutableList<Long>

    fun updateImageData(imageUrl:String,imageData :String):Long

    fun getPersons():MutableList<PersonWrapper>

}