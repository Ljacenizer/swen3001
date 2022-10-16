package com.example.clock

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.clock.databinding.StopwatchBinding
import java.util.*

class MainActivity : AppCompatActivity()
{
    lateinit var binding: StopwatchBinding
    lateinit var sendhelper: sendhelp

    private val timer = Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = StopwatchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sendhelper = sendhelp(applicationContext)

        binding.btnStart.setOnClickListener { startStopAction() }
        binding.btnReset.setOnClickListener { resetStopAction() }

        if(sendhelper.timerCount()){
            startTimer()
        }else{
            stopTimer()
            if(sendhelper.startTime() != null && sendhelper.stopTime() != null){
                val time = Date().time - calcRestartTime().time
                binding.txtTime.text = timeString(time)
            }
        }

        timer.scheduleAtFixedRate(timeTask(),0,500)
    }

    private inner class timeTask: TimerTask(){
        override fun run() {
            if (sendhelper.timerCount()){
                val time = Date().time - sendhelper.startTime()!!.time
                binding.txtTime.text =  timeString(time)
            }
        }
    }

    private fun resetStopAction() {
        sendhelper.setStopTime(null)
        sendhelper.setStopTime(null)
        stopTimer()
        binding.txtTime.text = timeString(0)
    }

    private fun stopTimer() {
        sendhelper.setCountTimer(false)
        binding.btnStart.text = getString(R.string.start)
    }
    private fun startTimer() {
        sendhelper.setCountTimer(true)
        binding.btnStart.text = getString(R.string.stop)
    }

    private fun startStopAction() {
        if(sendhelper.timerCount()){
            sendhelper.setStopTime(Date())
            stopTimer()
        }else{
            if(sendhelper.stopTime() != null){
                sendhelper.setStartTime(calcRestartTime())
                sendhelper.setStopTime(null)
            }else{
                sendhelper.setStartTime(Date())
            }
            startTimer()
        }
    }

    private fun calcRestartTime(): Date{
        val diff = sendhelper.startTime()!!.time - sendhelper.stopTime()!!.time
        return Date(System.currentTimeMillis() + diff)
    }

    private fun timeString(ms: Long): String?{
        val seconds = (ms / 1000) % 60
        val minutes = ((ms/1000/60) % 60)
        val hours = ((ms / 1000 / 60 / 60) % 24)
        
        return makeTimeString(hours,minutes,seconds)
    }

    private fun makeTimeString(hours: Long, minutes: Long, seconds: Long): String? {
        return String.format("%02d:%02d:%02d",hours,minutes,seconds)
    }
}