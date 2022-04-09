
package com.example.japanese_learn_app

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface SangakuAPI{
    @FormUrlEncoded
    @POST("/Sangaku")
    fun chatwithbot(@Field("chatInput")chatText:String): Call<SangakuResponse>
}

class SangakuResponse(val AoiReply:String)