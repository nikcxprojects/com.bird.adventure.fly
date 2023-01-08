package com.bird.adventure.fly

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.bird.adventure.fly.databinding.ActivityLoaderBinding
import java.util.*


class ActivityLoader : AppCompatActivity() {

    private lateinit var binding: ActivityLoaderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(
            (if (getSharedPreferences("prefs", MODE_PRIVATE)
                    .getBoolean("dark",false)) AppCompatDelegate.MODE_NIGHT_YES  else
                AppCompatDelegate.MODE_NIGHT_NO));
        super.onCreate(savedInstanceState)
        binding = ActivityLoaderBinding.inflate(layoutInflater)
        if(getSharedPreferences("prefs", MODE_PRIVATE)
                .getBoolean("dark",false)) binding.imageView3.setImageResource(R.mipmap.dark1_foreground)
        setContentView(R.layout.activity_loader)
        val updateThread = Thread {
            Thread.sleep(3000)
            startActivity(Intent(applicationContext,MainActivity::class.java))
        }
        updateThread.start()
    }
}