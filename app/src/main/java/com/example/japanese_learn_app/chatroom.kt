package com.example.japanese_learn_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.chat_room.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import android.view.View

import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.chat_list.*
import kotlinx.android.synthetic.main.chat_list.view.*


class chatroom : AppCompatActivity() {
    private val chatAdapter = chatadapter()
    //private  scrollView mscrollview;
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat_room)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://ac0e-34-91-110-130.ngrok.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val BasicAPIService=retrofit.create(APIconnector::class.java)
        val JPapiService=retrofit.create(JPChat::class.java)
        val BasicAPI=retrofit.create(BasicJPCOnnector::class.java)
        val advancedAPI=retrofit.create(AdvancedJPCOnnector::class.java)
        val Sangaku_API=retrofit.create(SangakuAPI::class.java)

        rectaChat_list.layoutManager=LinearLayoutManager(this)
        rectaChat_list.adapter = chatAdapter

        chatAdapter.addChatToList(Chatroom_model("Welcome to the chatroom. \n Welcome to Learn Japanese with Aoi! \n A: Start from Basic 50 Japanese words.\n B: Start from Learning Japanese vocabularies.\n C: Start from simple conversation.\n D: Try the problem set of Sangaku. \n Or you can simply type english to ask for help. For example, I am new learner" , true))

        var answer_open=0;
        //answer_open = 3 --> start JP cpnversation
        var answer=0;

        SendBtn.setOnClickListener{
            if(InputCHat.text.isNullOrBlank()){
                Toast.makeText(this@chatroom,"Please enter a text",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            //chatAdapter.addChatToList(InputCHat.text.toString())
            chatAdapter.addChatToList(Chatroom_model(InputCHat.text.toString(), false))
            //R.layout.chat_list.fullScroll(ScrollView.FOCUS_DOWN);
            //answer open to control which question mode that the users are reading
            // 1: Basic, 2: Advanced 3: Conversation 4: Sangaku

            val rv = findViewById<View>(R.id.rectaChat_list) as RecyclerView
            rv.smoothScrollToPosition(chatAdapter.getItemCount()-1)

            if (InputCHat.text.toString().uppercase()=="A"&&answer_open==0){
                BasicAPI.chatwithbot(InputCHat.text.toString()).enqueue(callBasicBack)
                answer_open=1;
                //previous_chatiput=1;
            }
            else if (InputCHat.text.toString().uppercase()=="B"&&answer_open==0){
                advancedAPI.chatwithbot(InputCHat.text.toString()).enqueue(callAdvancedBack)
                answer_open=2;
                //previous_chatiput=2;
            }
            else if(InputCHat.text.toString().uppercase()=="C"&&answer_open==0){
                JPapiService.chatwithbot(InputCHat.text.toString()).enqueue(callJPBack)
                answer_open=3;
               // previous_chatiput=3;
            }
            else if(InputCHat.text.toString().uppercase()=="D"&&answer_open==0){
                Sangaku_API.chatwithbot(InputCHat.text.toString()).enqueue(CallSangakuResponse)
                answer_open=4;

                //previous_chatiput=4;
            }
            else if(InputCHat.text.toString().uppercase()=="EXIT"){
                BasicAPIService.chatwithbot(InputCHat.text.toString()).enqueue(callBack)
                answer_open=0;
            }
            else if(answer_open==1){
                if(InputCHat.text.toString().uppercase()=="A") {
                    chatAdapter.addChatToList(Chatroom_model("Well done! Correct!If you want to continue, you can type next, otherwise, you can type exit to exit.", true))
                    answer = 1;
                    val rv = findViewById<View>(R.id.rectaChat_list) as RecyclerView
                    rv.smoothScrollToPosition(chatAdapter.getItemCount()-1)
                    //answer_open=0;
                }
                else if (InputCHat.text.toString().uppercase()=="NEXT" && answer == 1){
                    BasicAPI.chatwithbot("A").enqueue(callBasicBack)
                }
                else{
                    chatAdapter.addChatToList(Chatroom_model("This answer is wrong. Please try again!", true))
                    val rv = findViewById<View>(R.id.rectaChat_list) as RecyclerView
                    rv.smoothScrollToPosition(chatAdapter.getItemCount()-1)
                }
            }
            else if(answer_open==2){
                if(InputCHat.text.toString().uppercase()=="C") {
                    chatAdapter.addChatToList(Chatroom_model("Well done! Correct! If you want to continue, you can type next, otherwise, you can type exit to exit.", true))
                    answer = 1;
                    val rv = findViewById<View>(R.id.rectaChat_list) as RecyclerView
                    rv.smoothScrollToPosition(chatAdapter.getItemCount()-1)
                    //answer_open=0;
                }
                else if(InputCHat.text.toString().uppercase()=="NEXT"&& answer == 1){
                    advancedAPI.chatwithbot("B").enqueue(callAdvancedBack)
                }
                else{
                    chatAdapter.addChatToList(Chatroom_model("This answer is wrong. Please try again!", true))
                    val rv = findViewById<View>(R.id.rectaChat_list) as RecyclerView
                    rv.smoothScrollToPosition(chatAdapter.getItemCount()-1)
                }


            }else if(answer_open==3){
                JPapiService.chatwithbot(InputCHat.text.toString()).enqueue(callJPBack)
                if(InputCHat.text.toString().uppercase()=="EXIT"){
                    BasicAPIService.chatwithbot(InputCHat.text.toString()).enqueue(callBack)
                }
            }
            else if (answer_open==4){

                if(InputCHat.text.toString().uppercase()=="D") {
                    chatAdapter.addChatToList(Chatroom_model("Well done! Correct! If you want to continue, you can type next, otherwise, you can type exit to exit.", true))
                    answer = 1;
                    //answer_open=0;
                }
                else if(InputCHat.text.toString().uppercase()=="NEXT"&& answer == 1){
                    Sangaku_API.chatwithbot("NEXT").enqueue(CallSangakuResponse)
                }
                else{
                    chatAdapter.addChatToList(Chatroom_model("This answer is wrong. Please try again!", true))
                }

            }
            else {
                BasicAPIService.chatwithbot(InputCHat.text.toString()).enqueue(callBack)
            }

            InputCHat.text.clear()
    }
}

    private  val CallSangakuResponse = object :Callback<SangakuResponse>{
        override fun onResponse(call: Call<SangakuResponse>, response: Response<SangakuResponse>) {
            if(response.isSuccessful &&  response.body() != null){
                //response.body().chatBotReply
                // chatAdapter.addChatToList(response.body()!!.chatBotReply)
                chatAdapter.addChatToList(Chatroom_model(response.body()!!.AoiReply,true))
                val rv = findViewById<View>(R.id.rectaChat_list) as RecyclerView
                rv.smoothScrollToPosition(chatAdapter.getItemCount()-1)

            }else{
                Toast.makeText(this@chatroom,"Something went wrong",Toast.LENGTH_LONG).show()
            }
            //chatAdapter.addChatToList(InputCHat.text.toString())
        }

        override fun onFailure(call: Call<SangakuResponse>, t: Throwable) {
            Toast.makeText(this@chatroom,"Something went wrong Here",Toast.LENGTH_LONG).show()
         }

    }




    private  val callBasicBack = object :Callback<BasicChatResponse>{
        override fun onResponse(call: Call<BasicChatResponse>, response: Response<BasicChatResponse>) {
            if(response.isSuccessful &&  response.body() != null){
                //response.body().chatBotReply
                // chatAdapter.addChatToList(response.body()!!.chatBotReply)
                chatAdapter.addChatToList(Chatroom_model(response.body()!!.AoiReply,true))
                val rv = findViewById<View>(R.id.rectaChat_list) as RecyclerView
                rv.smoothScrollToPosition(chatAdapter.getItemCount()-1)
            }else{
                Toast.makeText(this@chatroom,"Something went wrong",Toast.LENGTH_LONG).show()
            }
            //chatAdapter.addChatToList(InputCHat.text.toString())
        }

        override fun onFailure(call: Call<BasicChatResponse>, t: Throwable) {
            Toast.makeText(this@chatroom,"Something went wrong Here",Toast.LENGTH_LONG).show()
        }

    }


    private  val callAdvancedBack = object :Callback<AdvancedChatResponse>{
        override fun onResponse(call: Call<AdvancedChatResponse>, response: Response<AdvancedChatResponse>) {
            if(response.isSuccessful &&  response.body() != null){
                //response.body().chatBotReply
                // chatAdapter.addChatToList(response.body()!!.chatBotReply)
                chatAdapter.addChatToList(Chatroom_model(response.body()!!.AoiReply,true))
                val rv = findViewById<View>(R.id.rectaChat_list) as RecyclerView
                rv.smoothScrollToPosition(chatAdapter.getItemCount()-1)
            }else{
                Toast.makeText(this@chatroom,"Something went wrong",Toast.LENGTH_LONG).show()
            }
            //chatAdapter.addChatToList(InputCHat.text.toString())
        }

        override fun onFailure(call: Call<AdvancedChatResponse>, t: Throwable) {
            Toast.makeText(this@chatroom,"Something went wrong Here",Toast.LENGTH_LONG).show()
        }

    }

    private  val callJPBack = object :Callback<JPChatResponse>{
        override fun onResponse(call: Call<JPChatResponse>, response: Response<JPChatResponse>) {
            if(response.isSuccessful &&  response.body() != null){
                //response.body().chatBotReply
                // chatAdapter.addChatToList(response.body()!!.chatBotReply)
                chatAdapter.addChatToList(Chatroom_model(response.body()!!.AoiReply,true))
                val rv = findViewById<View>(R.id.rectaChat_list) as RecyclerView
                rv.smoothScrollToPosition(chatAdapter.getItemCount()-1)
            }else{
                Toast.makeText(this@chatroom,"Something went wrong",Toast.LENGTH_LONG).show()
            }
            //chatAdapter.addChatToList(InputCHat.text.toString())
        }

        override fun onFailure(call: Call<JPChatResponse>, t: Throwable) {
            Toast.makeText(this@chatroom,"Something went wrong Here",Toast.LENGTH_LONG).show()
        }

    }



    private  val callBack = object :Callback<ChatResponse>{
        override fun onResponse(call: Call<ChatResponse>, response: Response<ChatResponse>) {
            if(response.isSuccessful &&  response.body() != null){
               //response.body().chatBotReply
                   // chatAdapter.addChatToList(response.body()!!.chatBotReply)
                chatAdapter.addChatToList(Chatroom_model(response.body()!!.AoiReply,true))
                val rv = findViewById<View>(R.id.rectaChat_list) as RecyclerView
                rv.smoothScrollToPosition(chatAdapter.getItemCount()-1)
            }else{
                Toast.makeText(this@chatroom,"Something went wrong",Toast.LENGTH_LONG).show()
            }
            //chatAdapter.addChatToList(InputCHat.text.toString())
        }

        override fun onFailure(call: Call<ChatResponse>, t: Throwable) {
           Toast.makeText(this@chatroom,"Something went wrong Here",Toast.LENGTH_LONG).show()
        }

    }


}