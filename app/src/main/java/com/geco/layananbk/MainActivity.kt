package com.geco.layananbk

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.geco.layananbk.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initFirebaseAuth()

        binding.apply {


            btnActivity.setOnClickListener {
                val intent = Intent(this@MainActivity, AktivitasActivity::class.java)
                startActivity(intent)
            }
            btnHome.setOnClickListener {
                val intent = Intent(this@MainActivity, HomeActivity::class.java)
                startActivity(intent)
            }
            btnProgres.setOnClickListener {
                val intent = Intent(this@MainActivity, ProgresActivity::class.java)
                startActivity(intent)
            }
            btnLogOut.setOnClickListener {
                val sharedPreference =  getSharedPreferences("login", Context.MODE_PRIVATE)
                val editor = sharedPreference.edit()
                editor.remove("isUserLogin")
                editor.apply()
                auth.signOut()
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
    private fun initFirebaseAuth() {
        auth = FirebaseAuth.getInstance()
    }
}