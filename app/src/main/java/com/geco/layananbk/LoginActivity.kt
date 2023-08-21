package com.geco.layananbk

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.geco.layananbk.databinding.ActivityLoginBinding
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initFirebaseAuth()
        cekLoginStatus()

        binding.apply {
            btnLogin.setOnClickListener {
                val email = inputUsernameLogin.text.toString()
                val password = inputPasswordLogin.text.toString()
                if (checkValidation(email, password)) {
                    loginToServer(email, password)
                }
            }
            tvRegister.setOnClickListener {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
            }
        }

    }

    private fun cekLoginStatus(){
        val sharedPreference =  getSharedPreferences("login", Context.MODE_PRIVATE)
        if(sharedPreference.contains("isUserLogin")){
            startActivity(Intent(this, MainActivity::class.java))
            finishAffinity()
        }
    }

    private fun loginToServer(email: String, pass: String) {
        val credential = EmailAuthProvider.getCredential(email, pass)
        fireBaseAuth(credential)
    }

    private fun fireBaseAuth(credential: AuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                CustomDialog.hideLoading()
                if (task.isSuccessful) {
                    val sharedPreference =  getSharedPreferences("login", Context.MODE_PRIVATE)
                    val editor = sharedPreference.edit()
                    editor.putBoolean("isUserLogin", true);
                    editor.apply()
                    startActivity(Intent(this, MainActivity::class.java))
                    finishAffinity()
                } else {
                    Toast.makeText(this, "Sign-In failed", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                CustomDialog.hideLoading()
                Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()
            }
    }
    private fun checkValidation(email: String, pass: String): Boolean {
        if (email.isEmpty()){
            binding.inputUsernameLogin.error = "Please Field your email"
            binding.inputUsernameLogin.requestFocus()
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.inputUsernameLogin.error = "Please use valid email"
            binding.inputUsernameLogin.requestFocus()
        }else if (pass.isEmpty()){
            binding.inputPasswordLogin.error = "Please field your password"
            binding.inputPasswordLogin.requestFocus()
        }else{
            return true
        }
        CustomDialog.hideLoading()
        return false
    }

    private fun initFirebaseAuth() {
        auth = FirebaseAuth.getInstance()
    }

}