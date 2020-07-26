package com.umarabbas.assistme

import android.app.Application
import java.io.File
import java.io.PrintStream

class appClass : Application() {
    override fun onCreate() {
        super.onCreate()
//        val file = File(this.getExternalFilesDir(null), "file.txt")
//        file.createNewFile()
//        Thread.setDefaultUncaughtExceptionHandler { t, e -> file.appendText("${e.message}\n\n${t}")}
    }
}