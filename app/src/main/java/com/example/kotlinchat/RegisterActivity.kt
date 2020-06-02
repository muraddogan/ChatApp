package com.example.kotlinchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var refUsers: DatabaseReference
    private var firebaseUserID: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val toolbar: Toolbar = findViewById(R.id.toolbar_register)
        setSupportActionBar(toolbar)
        supportActionBar!!.title="Register"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            val intent = Intent(this@RegisterActivity, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        mAuth = FirebaseAuth.getInstance()

        register_btn.setOnClickListener {
            registerUser()
        }
    }



    private fun registerUser()
    {
        val username: String = username_register.text.toString()
        val email: String = email_register.text.toString()
        val password: String = password_register.text.toString()

        if(username == "")
        {
            Toast.makeText(this@RegisterActivity,"Please write username.", Toast.LENGTH_LONG).show()
        }
        else if(email == "")
        {
            Toast.makeText(this@RegisterActivity,"Please write email.", Toast.LENGTH_LONG).show()
        }
        else if(password == "")
        {
            Toast.makeText(this@RegisterActivity,"Please write password.", Toast.LENGTH_LONG).show()
        }
        else
        {
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener{task ->
                    if(task.isSuccessful)
                    {
                        firebaseUserID = mAuth.currentUser!!.uid
                        refUsers = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUserID)

                        val userHashMap = HashMap<String, Any>()
                        userHashMap["uid"] = firebaseUserID
                        userHashMap["username"] = username
                        userHashMap["profile"] = "https://firebasestorage.googleapis.com/v0/b/kotlinchat-7d014.appspot.com/o/profile.png?alt=media&token=839a3d27-0b32-4046-8d3a-f51039f4255b"
                        userHashMap["cover"] = "https://firebasestorage.googleapis.com/v0/b/kotlinchat-7d014.appspot.com/o/cover.png?alt=media&token=3a795c1b-7c8e-4744-a140-75dfcca01d87"
                        userHashMap["status"] = "offline"
                        userHashMap["search"] = username.toLowerCase()
                        userHashMap["facebook"] = "https://www.facebook.com/"
                        userHashMap["instagram"] = "https://www.instagram.com/"
                        userHashMap["website"] = "https://www.google.com/"

                        refUsers.updateChildren(userHashMap)
                            .addOnCompleteListener { task ->
                                if(task.isSuccessful)
                                {
                                    val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                    startActivity(intent)
                                    finish()
                                }
                            }
                    }
                    else
                    {
                        Toast.makeText(this@RegisterActivity,"Error Message: " + task.exception!!.message.toString(), Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}
