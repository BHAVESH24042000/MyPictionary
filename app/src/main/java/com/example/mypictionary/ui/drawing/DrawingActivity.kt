package com.example.mypictionary.ui.drawing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mypictionary.R
import com.example.mypictionary.databinding.ActivityDrawingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DrawingActivity : AppCompatActivity() {

    private lateinit var binding :ActivityDrawingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDrawingBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}