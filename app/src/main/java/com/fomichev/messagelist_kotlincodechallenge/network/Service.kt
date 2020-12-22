package com.fomichev.messagelist_kotlincodechallenge.network;

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface MessageService {

    @GET("{file_name}")
    suspend fun getMessages(@Path("file_name") fileName: String?):  List<NetworkMessage>
}


object MessageNetwork{

    val API_ENDPOINT = NetworkInputDataManager.url

    private val retrofit = Retrofit.Builder()
            .baseUrl(API_ENDPOINT)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

    val messageService = retrofit.create(MessageService::class.java)

}

