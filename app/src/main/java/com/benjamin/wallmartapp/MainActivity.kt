package com.benjamin.wallmartapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var userList:ArrayList<User> = loadCredentials()
        val regIntent = intent

     var loginBtn = findViewById<Button>(R.id.btn_login)
        var forgot = findViewById<TextView>(R.id.forgot_pwd)
        var email = findViewById<EditText>(R.id.email)
        forgot.setOnClickListener{
            var email = email.text?.toString()
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.type = "text/plain"
            var userPwd:String? = ""
            for(u in userList){
                if(u.username.equals(email)){
                    userPwd = u.password;
                }
            }
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf( email));
            intent.putExtra(Intent.EXTRA_SUBJECT, "Password Reset");
            if(email !=null && userPwd !=null) {
                intent.putExtra(Intent.EXTRA_TEXT, "your password is "+userPwd)
                startActivity(intent)
            }

        }
        var createBtn = findViewById<Button>(R.id.create)

        loginBtn.setOnClickListener {

            var password = findViewById<EditText>(R.id.password)

            for(user in userList){
                if(user.username.equals(email.text.toString()) && user.password.equals(password.text.toString())){
                    val intent = Intent(this, ShoppingActivity::class.java)
                        intent.putExtra("username", email.text.toString())
                        startActivity(intent)
                }
            }
        }

        var resultContracts = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result->
            if(result.resultCode == Activity.RESULT_OK) {
                var res = result.data?.getSerializableExtra("user")
                if(res !=null){
                    var user = res as User
                    userList.add(user)

                }

            }

        }


        createBtn.setOnClickListener {

            val intent = Intent(this, RegisterActivity::class.java)
            resultContracts.launch(intent)
        }



    }

    fun loadCredentials():ArrayList<User> {
        var benjamin:User= User("benjamin","batte","bbatte@miu.edu","bate");
        var yas:User= User("yas","sekabi","yseka@miu.edu","bate");
        var ian:User= User("ian","smith","ismith@miu.edu","bate");
        var rose:User= User("rose","nambi","rnambi@miu.edu","bate");
        var jane:User= User("jane","namudu","janenamudu@gmail.com","bate");

        var userList = arrayListOf<User>(benjamin,yas,ian,rose,jane)
        return userList;
    }
}