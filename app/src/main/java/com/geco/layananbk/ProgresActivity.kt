package com.geco.layananbk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.geco.layananbk.databinding.ActivityProgresBinding

class ProgresActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProgresBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProgresBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnPeriksaHasil.setOnClickListener {
                Toast.makeText(this@ProgresActivity, "Belum Ada Tugas di selesaikan", Toast.LENGTH_SHORT).show()
            }
            btnProgresSelesai.setOnClickListener {
                val intent = Intent(this@ProgresActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}