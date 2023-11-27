package com.geco.layananbk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.geco.layananbk.databinding.ActivityEvaluasiUmumBinding

class EvaluasiUmumActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEvaluasiUmumBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEvaluasiUmumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply{
            tvEvaluasiUmum.setOnClickListener {
                val intent = Intent(this@EvaluasiUmumActivity, SoalEvaluasiUmumActivity::class.java)
                startActivity(intent)
            }
            btnKembaliKeMenu.setOnClickListener {
                val intent = Intent(this@EvaluasiUmumActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }

    }
}