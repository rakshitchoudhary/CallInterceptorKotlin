package com.callinterceptorkotlin

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.os.IBinder
import android.util.Log
import android.widget.Toast
class MyService : Service() {

    lateinit var receiver : CallReceiver

    override fun onCreate() {
        super.onCreate()

        receiver = CallReceiver()

        val intentFilter = IntentFilter()
        intentFilter.addAction("android.intent.action.PHONE_STATE");
        intentFilter.addAction(android.telephony.TelephonyManager.ACTION_PHONE_STATE_CHANGED);
        intentFilter.addAction("android.intent.action.NEW_OUTGOING_CALL");
        registerReceiver(receiver, intentFilter)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        onTaskRemoved(intent)
        return START_STICKY
    }
    override fun onBind(intent: Intent): IBinder? {
        // TODO: Return the communication channel to the service.
        throw UnsupportedOperationException("Not yet implemented")
    }
    override fun onTaskRemoved(rootIntent: Intent) {
        //Log.e("Service", "Running")
        val restartServiceIntent = Intent(applicationContext, this.javaClass)
        restartServiceIntent.setPackage(packageName)
        startService(restartServiceIntent)
        super.onTaskRemoved(rootIntent)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}