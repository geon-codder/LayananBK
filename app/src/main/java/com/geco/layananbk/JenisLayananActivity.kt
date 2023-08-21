package com.geco.layananbk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.geco.layananbk.databinding.ActivityJenisLayananBinding

class JenisLayananActivity : AppCompatActivity() {
    private lateinit var binding: ActivityJenisLayananBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJenisLayananBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}