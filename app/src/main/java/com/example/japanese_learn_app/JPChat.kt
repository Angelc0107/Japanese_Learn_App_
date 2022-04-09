package com.example.japanese_learn_app

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface JPChat {
    @FormUrlEncoded
    @POST("/JPconversation")
    fun chatwithbot(@Field("chatInput")chatText:String): Call<JPChatResponse>
}

//this ChatbotReply is from th pycharm chatbotAPI
class JPChatResponse(val AoiReply:String)