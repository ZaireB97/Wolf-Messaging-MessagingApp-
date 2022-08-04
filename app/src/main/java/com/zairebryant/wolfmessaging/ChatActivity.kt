package com.zairebryant.wolfmessaging

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatActivity : AppCompatActivity() {
    private lateinit var messageRecyclerView: RecyclerView
    private lateinit var messageBox: EditText
    private lateinit var sendButton: ImageView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>
    private lateinit var databaseReference: DatabaseReference

    /*
    Using sender room will make sure that the messages stay private between the two users
    Creates a unique room for the sender and the receiver(pair)
     */

    var receiverRoom:String? = null

    var senderRoom:String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val name = intent.getStringExtra("name")
        val receiverUserId = intent.getStringExtra("userId")

        val senderUserid = FirebaseAuth.getInstance().currentUser?.uid

        databaseReference = FirebaseDatabase.getInstance().getReference()

        senderRoom = receiverUserId + senderUserid

        receiverRoom = senderUserid + receiverUserId


        supportActionBar?.title = name

        messageList = ArrayList()
        messageAdapter = MessageAdapter(this,messageList)
        messageRecyclerView = findViewById(R.id.recycler_view_chat)
        messageBox = findViewById(R.id.edit_text_message_box)
        sendButton = findViewById(R.id.image_view_send_button)

        messageRecyclerView.layoutManager = LinearLayoutManager(this)
        messageRecyclerView.adapter = messageAdapter




        //Getting a reference to the message data and adding to the recyclerView

        /*snapshot is used to get all the values in the database
        .We have to loop through all the messages in the database
         */
        databaseReference.child("chat").child(senderRoom!!).child("messages").addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                messageList.clear()

                //This snapshot will contain all the messages
                for(postSnapshot in snapshot.children){

                    val message = postSnapshot.getValue(Message::class.java)

                    messageList.add(message!!)
                }

                messageAdapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })



        sendButton.setOnClickListener(){

            //First we have to add the message to the database
            //Get the Message from the message box
            val message  = messageBox.text.toString()

            //Create the message object so that we can pass it to the database
            val messageObject = Message(message,senderUserid)

            /*Here we are creating a new child node for the chat
            Then within that we have a child node for the sender room.
            Then a child node for all messages
            'Push' will create a unique node everytime
             */
            databaseReference.child("chat").child(senderRoom!!).child("messages").push().setValue(messageObject).addOnSuccessListener {
                databaseReference.child("chat").child(receiverRoom!!).child("messages").push().setValue(messageObject)
            }

            /*
            We have to update the receiver room at the same time. Since both user UIs have to be updated
            (sender and receiver). This is why we use addOnSuccessListener. This will ensure that the receiver
            room is updated everytime that the sender room is updated
             */

            //Set a blank text for the message box after the message button has been clicked


            messageBox.setText(" ")




        }
    }
}