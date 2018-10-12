package com.i2e1.linq.data.Network

import com.i2e1.linq.Utils.Constants
import com.i2e1.linq.data.Pojo.PersonsApiResponse
import retrofit2.Call
import retrofit2.http.GET


interface APIInterface {

    @GET(Constants.persons_api_extension_url)
    fun getPersons(): Call<PersonsApiResponse>

}