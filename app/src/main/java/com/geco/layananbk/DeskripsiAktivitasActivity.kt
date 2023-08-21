package com.geco.layananbk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.geco.layananbk.databinding.ActivityDeskripsiAktivitasBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class DeskripsiAktivitasActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDeskripsiAktivitasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeskripsiAktivitasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply{
            btnDeskripsiAktivitasSelesai.setOnClickListener {
                val namaSiswa = inputNamaSiswa.text.toString()
                val kelasSiswa = inputKelasSiswa.text.toString()
                val tanggalLayanan = inputTanggalLayananSiswa.text.toString()
                val isiKonsultasi = inputIsiKonsultasiSiswa.text.toString()

                if(checkValidation(namaSiswa, kelasSiswa, tanggalLayanan, isiKonsultasi)){
                    Toast.makeText(this@DeskripsiAktivitasActivity,"Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
                }else {
                    val database = FirebaseDatabase.getInstance()
                    val myRef = database.getReference("dataUser")
                    val getUserEmail = Firebase.auth.currentUser?.email.toString()
                    myRef.child("users").child(encodeUserEmail(getUserEmail).toString()).child("Layanan")
                        .child("DeskripsiAktivitas").child("namaSiswa").setValue(namaSiswa)
                    myRef.child("users").child(encodeUserEmail(getUserEmail).toString()).child("Layanan")
                        .child("DeskripsiAktivitas").child("kelasSiswa").setValue(kelasSiswa)
                    myRef.child("users").child(encodeUserEmail(getUserEmail).toString()).child("Layanan")
                        .child("DeskripsiAktivitas").child("tanggalLayanan").setValue(tanggalLayanan)
                    myRef.child("users").child(encodeUserEmail(getUserEmail).toString()).child("Layanan")
                        .child("DeskripsiAktivitas").child("isiKonsultasi").setValue(isiKonsultasi)


                    val intent = Intent(this@DeskripsiAktivitasActivity, IsiEvaluasiActivity::class.java)
                    startActivity(intent)
                }

            }
        }
    }

    private fun encodeUserEmail(userEmail: String): String? {
        return userEmail.replace(".", ",")
    }

    private fun checkValidation(namaSiswa: String, kelasSiswa: String, tanggalLayanan: String, isiKonsultasi: String): Boolean {
        if (namaSiswa.isEmpty()) {
            binding.inputNamaSiswa.error = "Silakan lengkapi Nama anda"
            binding.inputNamaSiswa.requestFocus()
        } else if (kelasSiswa.isEmpty()) {
            binding.inputKelasSiswa.error = "Silakan lengkapi Kelas anda"
            binding.inputKelasSiswa.requestFocus()
        }else if (tanggalLayanan.isEmpty()) {
            binding.inputTanggalLayananSiswa.error = "Silakan lengkapi Kelas anda"
            binding.inputTanggalLayananSiswa.requestFocus()
        }else if (isiKonsultasi.isEmpty()) {
            binding.inputIsiKonsultasiSiswa.error = "Silakan lengkapi Kelas anda"
            binding.inputIsiKonsultasiSiswa.requestFocus()
        }
        return false
    }
}