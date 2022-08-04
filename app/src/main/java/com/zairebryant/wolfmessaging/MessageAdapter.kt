package com.zairebryant.wolfmessaging

import android.content.ClipData
import android.content.Context
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context:Context, val messageList: ArrayList<com.zairebryant.wolfmessaging.Message>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_RECEIVE = 1
    val ITEM_SENT = 2

    class SentViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val sentMessageView = itemView.findViewById<TextView>(R.id.text_view_send_message)


    }

    class ReceivedViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

        val receivedMessageView = itemView.findViewById<TextView>(R.id.text_view_receive_message)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == 1){

            //inflate receive

            val view:View = LayoutInflater.from(context).inflate(R.layout.recieve_message_item,parent,false)

            return ReceivedViewHolder(view)



        }
        else{

            //inflate send

            val view:View = LayoutInflater.from(context).inflate(R.layout.send_item_layout,parent,false)

            return SentViewHolder(view)


        }




    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.javaClass == SentViewHolder::class.java){
            val viewHolder = holder as SentViewHolder

            val currentMessage = messageList[position]

            viewHolder.sentMessageView.text = currentMessage.message



        }
         else{

             val viewHolder = holder as ReceivedViewHolder

            val currentMessage = messageList[position]

            viewHolder.receivedMessageView.text = currentMessage.message



        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]

        return if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)){
            ITEM_SENT
        } else{

            ITEM_RECEIVE
        }
    }
}