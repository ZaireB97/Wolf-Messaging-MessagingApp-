package com.zairebryant.wolfmessaging

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import java.nio.file.Files.size

class UserAdapter(val context:Context,val userList:ArrayList<User>):RecyclerView.Adapter<UserAdapter.UserViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view:View = LayoutInflater.from(context).inflate(R.layout.activity_user_layout_item,parent,false)

        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = userList[position]
        holder.name.text = currentUser.name

        holder.itemView.setOnClickListener(){
            val intent = Intent(context,ChatActivity::class.java)

            //Pass the user Id of the user that you clicked so that you can send them a message
            intent.putExtra("name",currentUser.name)
            intent.putExtra("userId",currentUser.userId)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class UserViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val name = itemView.findViewById<TextView>(R.id.text_view_user_name)

    }
}