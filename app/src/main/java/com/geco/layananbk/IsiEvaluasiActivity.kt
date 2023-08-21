package com.geco.layananbk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.geco.layananbk.databinding.ActivityIsiEvaluasiBinding

class IsiEvaluasiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIsiEvaluasiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIsiEvaluasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnLayananDasar.setOnClickListener {
                val intent = Intent(this@IsiEvaluasiActivity, IsiEvaluasiLayananDasarActivity::class.java)
                startActivity(intent)
            }
            btnLayananPeminatanDanPerencanaanIndividual.setOnClickListener {
                val intent = Intent(this@IsiEvaluasiActivity, IsiEvaluasiLayananPeminatanDanPerencanaanIndividualActivity::class.java)
                startActivity(intent)
            }
            btnLayananResponsif.setOnClickListener {
                val intent = Intent(this@IsiEvaluasiActivity, IsiEvaluasiLayananResponsifActivity::class.java)
                startActivity(intent)
            }
            btnLayananDukunganSistem.setOnClickListener {
                val intent = Intent(this@IsiEvaluasiActivity, IsiEvaluasiLayananDukunganSistemActivity::class.java)
                startActivity(intent)
            }
            btnIsiEvaluasiSelesai.setOnClickListener {
                val intent = Intent(this@IsiEvaluasiActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}