package com.example.stagetech.sourceDeDonnées.ServiceApi

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit {



//    val serviceApi: Api by lazy {
//        Retrofit.Builder()
//            .baseUrl("http://localhost:3000/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(Api::class.java)


        val retrofit = Retrofit.Builder()
            //.baseUrl("http://localhost:3000/")
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val serviceApi = retrofit.create(Api::class.java)



}