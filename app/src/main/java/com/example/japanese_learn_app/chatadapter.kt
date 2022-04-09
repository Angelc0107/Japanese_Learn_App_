package com.example.japanese_learn_app

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.chat_list.view.*

class chatadapter: RecyclerView.Adapter<chatadapter.MyViewHolder>() {
    //private val list = ArrayList<String>()
        private val list = ArrayList<Chatroom_model>()

        inner class MyViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.chat_list,parent,false)){
         //fun integrate(chat:String) = with(itemView){
            fun integrate(chat:Chatroom_model) = with(itemView){
                //call the components from the xml
             // to make the chatmodel UI into a good one
             if(chat.isBot == true) {
                 //txtCHATBOT.textAlignment = View.TEXT_ALIGNMENT_VIEW_END

                 txtCHATBOT.setBackgroundColor(Color.rgb(173, 216, 230))
                 txtCHATBOT.setTextColor(Color.BLACK)
                 txtCHATBOT.setTextSize(18F)
                 txtCHATBOT.text = chat.chat
                 chatbot_image.setImageResource(R.drawable.aoi);


             }else{
                 txtCHATBOT.setBackgroundColor(Color.WHITE)
                 txtCHATBOT.setTextColor(Color.BLACK)
                 txtCHATBOT.setTextSize(18F)
                 txtCHATBOT.text = chat.chat
                 chatbot_image.setImageResource(R.drawable.user_icon);
             }



            }

        }



        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyViewHolder(parent)

        override fun onBindViewHolder(pareant_holder: MyViewHolder, pos: Int) {
            pareant_holder.integrate(list[pos])

        }

        override fun getItemCount() = list.size
        //  fun addChatToList(chat: String)
        fun addChatToList(botchat: Chatroom_model) {
            list.add(botchat)
            notifyDataSetChanged()
        }


}