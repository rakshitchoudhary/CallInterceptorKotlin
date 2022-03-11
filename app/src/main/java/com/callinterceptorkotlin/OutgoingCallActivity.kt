package com.callinterceptorkotlin

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class OutgoingCallActivity : AppCompatActivity() {

    var call = ""
    var code = ""

    lateinit var txt_call: TextView
    lateinit var txt_code: TextView

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.outgoingcallactivity)

        call = intent.getStringExtra("Calls").toString()
        code = intent.getStringExtra("Code").toString()

        txt_call = findViewById(R.id.txt_call)
        txt_code = findViewById(R.id.txt_code)

        title = "Call Interception"

        txt_call.setText(call)
        txt_code.setText(code)
    }
}