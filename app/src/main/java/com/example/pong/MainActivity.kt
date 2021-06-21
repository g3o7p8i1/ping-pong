package com.example.pong

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.pong.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        loadData()

        binding.startBtn.setOnClickListener {

            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
            finish()
        }


    }

    private fun loadData(){
        val sharedPreferances:SharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)?:return
        val highScore:Int=sharedPreferances.getInt("HighScore",0)
        binding.highScore.text ="High Score: $highScore"
    }
}
