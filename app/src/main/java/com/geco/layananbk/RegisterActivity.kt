package com.geco.layananbk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.geco.layananbk.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initFirebaseAuth()


        binding.apply {
            btnRegister.setOnClickListener {

                val email = inputUsernameRegister.text.toString()
                val pass = inputPasswordRegister.text.toString()
                val confirmPass = inputConfirmPasswordRegister.text.toString()
                val nama = inputNama.text.toString()
                val nisn = inputNISN.text.toString()
                val kelas = inputKelas.text.toString()
                val alamat = inputAlamat.text.toString()
                val notelp = inputNoTelp.text.toString()
                val sekolah = inputSekolah.text.toString()

                CustomDialog.showLoading(this@RegisterActivity)
                if (checkValidation(email, pass, confirmPass, nama, nisn, kelas, alamat, notelp, sekolah)){
                    registerToServer(email, pass, nama, nisn, kelas, alamat, notelp, sekolah)
                }

            }
            tvSudahpunyaAkun.setOnClickListener {
                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            }
        }
    }
    private fun registerToServer(email: String, pass: String, nama: String, nisn: String, kelas: String, alamat: String, notelp: String, sekolah: String) {
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener{task ->
                CustomDialog.hideLoading()
                if (task.isSuccessful){
                    val database = FirebaseDatabase.getInstance()
                    val myRef = database.getReference("dataUser")
                    val user = email

                    val dataSiswa = DataSiswa(user,nama, nisn, kelas, alamat,notelp, sekolah)

                    myRef.child("users").child(encodeUserEmail(email).toString()).child("biodata")
                        .setValue(dataSiswa)

                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                    finishAffinity()
                }
            }
            .addOnFailureListener{
                CustomDialog.hideLoading()
                Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
            }
    }

    fun encodeUserEmail(userEmail: String): String? {
        return userEmail.replace(".", ",")
    }

    private fun initFirebaseAuth() {
        auth = FirebaseAuth.getInstance()
    }

    private fun checkValidation(email: String, pass: String, confirmPass: String, nama: String, nisn: String, kelas: String, alamat: String, notelp: String, sekolah: String): Boolean {

        if (email.isEmpty()){
            binding.inputUsernameRegister.error = "Please field your email"
            binding.inputUsernameRegister.requestFocus()
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.inputUsernameRegister.error = "Please use valid email"
            binding.inputUsernameRegister.requestFocus()
        }else if (pass.isEmpty()){
            binding.inputPasswordRegister.error = "Please field your password"
            binding.inputPasswordRegister.requestFocus()
        }else if (confirmPass.isEmpty()){
            binding.inputConfirmPasswordRegister.error = "Please field your confirm password"
            binding.inputConfirmPasswordRegister.requestFocus()
        }else if (pass != confirmPass){
            binding.inputPasswordRegister.error = "Your pass didnt match"
            binding.inputConfirmPasswordRegister.error = "Your confirm pass didnt match"

            binding.inputPasswordRegister.requestFocus()
            binding.inputConfirmPasswordRegister.requestFocus()
        }else if (nama.isEmpty()){
            binding.inputNama.error = "Silakan lengkapi Nama anda"
            binding.inputNama.requestFocus()
        }else if (nisn.isEmpty()){
            binding.inputNISN.error = "Silakan lengkapi NISN anda"
            binding.inputNISN.requestFocus()
        }else if (kelas.isEmpty()){
            binding.inputKelas.error = "Silakan lengkapi Kelas anda"
            binding.inputKelas.requestFocus()
        }else if (alamat.isEmpty()){
            binding.inputAlamat.error = "Silakan lengkapi Alamat anda"
            binding.inputAlamat.requestFocus()
        }else if (notelp.isEmpty()){
            binding.inputNoTelp.error = "Silakan lengkapi No telpon anda"
            binding.inputNoTelp.requestFocus()
        }else if (sekolah.isEmpty()){
            binding.inputSekolah.error = "Silakan lengkapi Sekolah anda"
            binding.inputSekolah.requestFocus()
        }else{
            binding.inputPasswordRegister.error = null
            binding.inputConfirmPasswordRegister.error = null
            return true
        }
        CustomDialog.hideLoading()
        return false
    }
}