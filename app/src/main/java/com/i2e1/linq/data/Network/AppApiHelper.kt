package com.grampower.menuapplication.data.network


import com.i2e1.linq.data.Network.APIInterface
import com.i2e1.linq.data.Network.ApiClient
import com.i2e1.linq.data.Network.ApiHelper
import com.i2e1.linq.data.Pojo.PersonsApiResponse
import retrofit2.Call


class AppApiHelper : ApiHelper {

    var apiInterface: APIInterface
    init {
        apiInterface= ApiClient().getClient().create(APIInterface::class.java)
    }

    override fun getPersons(): Call<PersonsApiResponse> =apiInterface.getPersons()


}