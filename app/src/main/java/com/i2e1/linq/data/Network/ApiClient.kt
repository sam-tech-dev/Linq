package com.i2e1.linq.data.Network

import com.i2e1.linq.Utils.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class ApiClient {

    var client = OkHttpClient.Builder().addInterceptor(object : Interceptor {
        override fun intercept(chain: Interceptor.Chain?): Response {
            val newRequest = chain!!.request().newBuilder().build()
            return chain.proceed(newRequest)
        }

    }).build()

    fun getClient(): Retrofit {

        return Retrofit.Builder()
                .baseUrl(Constants.persons_api_base_url)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

}