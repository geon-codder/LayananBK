package com.geco.layananbk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.geco.layananbk.databinding.ActivityAktivitas2Binding

class AktivitasActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAktivitas2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAktivitas2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply{
            tvDeskripsiAktivitas.setOnClickListener {
                val intent = Intent(this@AktivitasActivity, DeskripsiAktivitasActivity::class.java)
                startActivity(intent)
            }
            tvIsiEvaluasi.setOnClickListener {
                val intent = Intent(this@AktivitasActivity, EvaluasiUmumActivity::class.java)
                startActivity(intent)
            }
            btnKembaliKeMenu.setOnClickListener {
                val intent = Intent(this@AktivitasActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }

    }
}