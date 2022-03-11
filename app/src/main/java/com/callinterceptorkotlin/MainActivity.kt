package com.callinterceptorkotlin

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title = "Call Interception"

        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ANSWER_PHONE_CALLS) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, android.Manifest.permission.PROCESS_OUTGOING_CALLS) != PackageManager.PERMISSION_GRANTED) {
                requestPermission()
            } else {
                startForegroundService(Intent(baseContext, MyService::class.java))
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestPermission() {
        if (shouldShowRequestPermissionRationale(android.Manifest.permission.PROCESS_OUTGOING_CALLS)) {
            requestPermissions(
                arrayOf(
                    android.Manifest.permission.ANSWER_PHONE_CALLS,
                    android.Manifest.permission.READ_CALL_LOG,
                    android.Manifest.permission.CALL_PHONE,
                    android.Manifest.permission.READ_PHONE_STATE,
                    android.Manifest.permission.PROCESS_OUTGOING_CALLS
                ), 1)
        } else {
            requestPermissions(
                arrayOf(
                    android.Manifest.permission.ANSWER_PHONE_CALLS,
                    android.Manifest.permission.READ_CALL_LOG,
                    android.Manifest.permission.CALL_PHONE,
                    android.Manifest.permission.READ_PHONE_STATE,
                    android.Manifest.permission.PROCESS_OUTGOING_CALLS
                ), 1)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> startForegroundService(Intent(baseContext, MyService::class.java))
        }
    }
}