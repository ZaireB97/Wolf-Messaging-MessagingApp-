package com.zairebryant.wolfmessaging

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var logoIV:ImageView
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin:Button
    private lateinit var buttonSignUp:Button
    private lateinit var hideImageView: ImageView
    private lateinit var authorization:FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        supportActionBar?.hide()

        editTextEmail = findViewById(R.id.edit_text_email)
        editTextPassword = findViewById(R.id.edit_text_password)
        buttonLogin = findViewById(R.id.button_log_in)
        buttonSignUp = findViewById(R.id.button_sign_up)
        logoIV = findViewById(R.id.image_view_logo)


        authorization = FirebaseAuth.getInstance()

        editTextPassword.transformationMethod = PasswordTransformationMethod.getInstance()



        // on below line we are setting background color to transparent
        logoIV.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent))


        //on click listener for sign up button
        buttonSignUp.setOnClickListener(){

            val signUpIntent = Intent(this,SignUpActivity::class.java)

            startActivity(signUpIntent)
        }

        buttonLogin.setOnClickListener(){
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            login(email,password)
        }
    }

    private fun login(email: String, password: String) {

        authorization.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information

                    val intent = Intent(this,MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(this,"User does not exist",Toast.LENGTH_LONG).show()

                }
            }



    }
}