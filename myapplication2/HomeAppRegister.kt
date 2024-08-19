package com.example.myapplication2

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager

// Попытка в главное окно
class HomeAppRegister {
    fun registerHomeApp(context: Context) {
        val packageName = context.packageName
        val intent = Intent("android.intent.action.MAIN")
        intent.addCategory("android.intent.category.HOME")
        intent.setPackage(packageName)
        context.packageManager.setComponentEnabledSetting(
            ComponentName(packageName, "com.example.myapplication2.MainActivity"),//packageName + "kotlin+kava.MainActivity"),
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
    }
}