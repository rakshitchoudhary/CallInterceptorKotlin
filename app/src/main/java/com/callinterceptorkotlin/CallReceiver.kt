package com.callinterceptorkotlin

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.os.Build
import android.telecom.TelecomManager
import android.telephony.PhoneNumberUtils
import android.telephony.TelephonyManager
import androidx.annotation.RequiresApi
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber
import java.lang.Exception

class CallReceiver: BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onReceive(context: Context?, p1: Intent?) {

        val newState = p1?.getStringExtra(TelephonyManager.EXTRA_STATE)

        if (TelephonyManager.EXTRA_STATE_OFFHOOK.equals(newState)) {
            var tm: TelephonyManager = context?.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

            val countryCodeValue = tm.getNetworkCountryIso()

            val phoneNumber = p1?.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)

            if (phoneNumber != null
                && !phoneNumber.isEmpty()
                && !phoneNumber.equals("null")) {
                var util: PhoneNumberUtil = PhoneNumberUtil.getInstance()
                var pn: Phonenumber.PhoneNumber

                try {
                    pn = util.parseAndKeepRawInput(phoneNumber, countryCodeValue.toUpperCase())

                    var activity = Class.forName("com.callinterceptorkotlin.OutgoingCallActivity")

                    var telecomManager: TelecomManager = context.getSystemService(Context.TELECOM_SERVICE) as TelecomManager
                    telecomManager.endCall()

                    var i:Intent = Intent()
                    i.setClassName(context, activity.name)
                    i.putExtra("Calls", phoneNumber)
                    i.putExtra("Code", pn.getCountryCode())
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(i)

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}