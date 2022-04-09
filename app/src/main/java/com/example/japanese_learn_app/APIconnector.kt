package com.example.japanese_learn_app

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface APIconnector {
    @FormUrlEncoded
    @POST("/chatroom")
    fun chatwithbot(@Field("chatInput")chatText:String): Call<ChatResponse>

}
//this ChatbotReply is from th pycharm chatbotAPI
class ChatResponse(val AoiReply:String)