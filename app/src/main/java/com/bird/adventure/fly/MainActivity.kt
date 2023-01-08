package com.bird.adventure.fly

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.bird.adventure.fly.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(
            (if (getSharedPreferences("prefs", MODE_PRIVATE)
                    .getBoolean("dark", false)
            ) AppCompatDelegate.MODE_NIGHT_YES else
                AppCompatDelegate.MODE_NIGHT_NO)
        );
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        if (getSharedPreferences("prefs", MODE_PRIVATE)
                .getBoolean("dark", false)
        ) binding.imageView3.setImageResource(R.mipmap.dark1_foreground)
        setContentView(binding.root)
    }

    public fun dark() {
        binding.imageView3.setImageResource(R.mipmap.dark1_foreground)
    }

    public fun light() {
        binding.imageView3.setImageResource(R.mipmap.light1_foreground)
    }
}