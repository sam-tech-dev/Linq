package com.i2e1.linq.data.Network

import com.i2e1.linq.data.Pojo.PersonsApiResponse
import retrofit2.Call


interface ApiHelper {

    fun getPersons(): Call<PersonsApiResponse>

}