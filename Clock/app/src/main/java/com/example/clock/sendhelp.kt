package com.example.clock

import android.content.Context
import android.content.SharedPreferences
import java.text.SimpleDateFormat
import java.util.*

class sendhelp(context: Context){
    private var sharedPref :SharedPreferences = context.getSharedPreferences(PREFERENCES,Context.MODE_PRIVATE)
    private var dateFormat = SimpleDateFormat("MM/dd/yyy HH:mm:ss", Locale.getDefault())

    private var timerCount = false
    private var startTime: Date? = null
    private var stopTime: Date? = null


    init{
        timerCount = sharedPref.getBoolean(countingKey,false)

        val startString = sharedPref.getString(startTimeKey,null)
        if(startString != null)
            startTime = dateFormat.parse(startString)

        val stopString = sharedPref.getString(stopTimeKey,null)
        if(stopString != null)
            stopTime = dateFormat.parse(stopString)
    }

    fun startTime(): Date? = startTime
    fun setStartTime(date: Date?){
        startTime = date
        with(sharedPref.edit()){
            val stringDate = if(date == null) null else dateFormat.format(date)
            putString(startTimeKey,stringDate)
            apply()
        }
    }


    fun stopTime(): Date? = stopTime
    fun setStopTime(date: Date?){
        stopTime = date
        with(sharedPref.edit()){
            val stringDate = if(date == null) null else dateFormat.format(date)
            putString(stopTimeKey,stringDate)
            apply()
        }
    }


    fun timerCount(): Boolean = timerCount
    fun setCountTimer(value: Boolean){
        timerCount = value
        with(sharedPref.edit()){
            putBoolean(countingKey,value)
            apply()
        }
    }


    companion object{
        const val PREFERENCES = "prefs"
        const val startTimeKey = "startkey"
        const val stopTimeKey = "stopkey"
        const val countingKey = "countkey"
    }
}