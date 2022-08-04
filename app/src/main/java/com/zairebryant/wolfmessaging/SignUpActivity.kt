package com.zairebryant.wolfmessaging

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var logoIV: ImageView
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonSignUp: Button
    private lateinit var authorization: FirebaseAuth
    private lateinit var databaseReference:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        supportActionBar?.hide()


        editTextName = findViewById(R.id.edit_text_user_name)
        editTextEmail = findViewById(R.id.edit_text_email)
        editTextPassword = findViewById(R.id.edit_text_password)
        buttonSignUp = findViewById(R.id.button_sign_up)
        logoIV = findViewById(R.id.image_view_logo)
        authorization = FirebaseAuth.getInstance()

        buttonSignUp.setOnClickListener(){
            val name = editTextName.text.toString()
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            SignUp(name,email,password)
        }

    }


    //Creating a user in the firebase
    private fun SignUp(name:String,email: String, password: String) {

        authorization.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information

                    //Add the user to the database
                    addUserToDatabase(name,email,authorization.currentUser?.uid!!)

                    val mainActivityIntent = Intent(this,MainActivity::class.java)
                    //We want to finish the login activity
                    finish()
                    startActivity(mainActivityIntent)


                } else {
                    Toast.makeText(this,"Some error occurred",Toast.LENGTH_LONG).show()

                }
            }


    }

    private fun addUserToDatabase(name: String, email: String, uid: String) {

        databaseReference = FirebaseDatabase.getInstance().getReference()

        //Creates child node in the data, and the parent is user
        databaseReference.child("user").child(uid).setValue(User(name,email, uid))




    }


}