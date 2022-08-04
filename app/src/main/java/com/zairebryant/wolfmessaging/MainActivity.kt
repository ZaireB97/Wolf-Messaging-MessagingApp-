package com.zairebryant.wolfmessaging

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var authorization:FirebaseAuth


    private lateinit var recyclerView: RecyclerView
    private lateinit var userList: ArrayList<User>
    private lateinit var userAdapter:UserAdapter
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        userList = ArrayList()
        userAdapter = UserAdapter(this,userList)
        recyclerView = findViewById(R.id.recycler_view_user)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = userAdapter




        authorization = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference()

        databaseReference.child("user").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                userList.clear()
                for(postSnapshot in snapshot.children){


                    val currentUser = postSnapshot.getValue(User::class.java)


                    if(authorization.currentUser?.uid != currentUser?.userId){
                        userList.add(currentUser!!)

                    }


                }
                userAdapter.notifyDataSetChanged()


            }

            override fun onCancelled(error: DatabaseError) {

            }


        })


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
       menuInflater.inflate(R.menu.activity_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.item_log_out){
            //logout
            authorization.signOut()
            val intent = Intent(this,LoginActivity::class.java)
            finish()
            startActivity(intent)
            return true
        }
        return true
    }
}