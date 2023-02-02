package com.ukadovlad21.mycoroutines1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import com.ukadovlad21.mycoroutines1.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            Log.d("MainActivity", msg.toString())
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.buttonLoad.setOnClickListener {
            loadData()
        }
        handler.handleMessage(Message.obtain(handler, 0, 20))
    }

    private fun loadData() {
        binding.progress.isVisible = true
        binding.buttonLoad.isEnabled = false
        val city = loadCity { city ->

            binding.tvLocation.text = city
            val temp = loadTemperature(city) { temp ->

                binding.tvTemperature.text = temp.toString()
                binding.progress.isVisible = false
                binding.buttonLoad.isEnabled = true
            }
        }
    }

    private fun loadCity(callback: (String) -> Unit) {
        thread {
            Thread.sleep(5000)
            runOnUiThread { // Handler(Looper.getMainLooper()).post {
                callback.invoke("Moscow")
            }
        }
    }

    private fun loadTemperature(city: String, callback: (Int) -> Unit) {
        thread {
            runOnUiThread { // Handler(Looper.getMainLooper()).post {
                Toast.makeText(
                    this,
                    "${getString(R.string.loading_temperature_toast)} to $city",
                    Toast.LENGTH_SHORT
                ).show()
            }

            Thread.sleep(5000)
            runOnUiThread { // Handler(Looper.getMainLooper()).post {
                callback.invoke(17)
            }
        }
    }
}