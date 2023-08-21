package com.geco.layananbk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.geco.layananbk.databinding.ActivityHomeBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference

        setUserData()
        binding.apply{

            btnMenuSelesai.setOnClickListener {
                startActivity(Intent(this@HomeActivity, MainActivity::class.java))
                finishAffinity()
            }
        }
    }
    private fun setUserData(){
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("dataUser")
        val getUserEmail = Firebase.auth.currentUser?.email.toString()
        if (getUserEmail != null) {
            myRef.child("users").child(encodeUserEmail(getUserEmail).toString()).child("biodata").get().addOnSuccessListener {
                if (it.exists()) {
                    Toast.makeText(this@HomeActivity, "Data Ditemukan", Toast.LENGTH_SHORT)
                        .show()
                    binding.tvNamaSiswa.text = it.child("nama").value.toString()
                    binding.tvNamaSiswa.visibility = View.VISIBLE
                    binding.tvNISNSiswa.text = it.child("nis").value.toString()
                    binding.tvNISNSiswa.visibility = View.VISIBLE
                    binding.tvKelasSiswa.text = it.child("kelas").value.toString()
                    binding.tvKelasSiswa.visibility = View.VISIBLE
                    binding.tvAlamatSiswa.text = it.child("alamat").value.toString()
                    binding.tvAlamatSiswa.visibility = View.VISIBLE
                    binding.tvNoTelpSiswa.text = it.child("notelp").value.toString()
                    binding.tvNoTelpSiswa.visibility = View.VISIBLE
                    binding.tvSekolahSiswa.text = it.child("sekolah").value.toString()
                    binding.tvSekolahSiswa.visibility = View.VISIBLE

                } else {
                    Toast.makeText(this@HomeActivity, "Data Belum Ditemukan", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                Log.e("firebase", "Error getting data", it)
            }
            myRef.child("users").child(encodeUserEmail(getUserEmail).toString()).child("Layanan")
                .child("DeskripsiAktivitas").get().addOnSuccessListener {
                if (it.exists()) {
                    binding.tvDeskripsiAktivitasSiswa.text = it.child("isiKonsultasi").value.toString()
                    binding.tvWaktuPelaksanaanSiswa.text = it.child("tanggalLayanan").value.toString()
                }
            }.addOnFailureListener {
                Log.e("firebase", "Error getting data", it)
            }
            myRef.child("users").child(encodeUserEmail(getUserEmail).toString()).child("Layanan")
                .child("Isi Evaluasi").get().addOnSuccessListener {
                    if (it.exists()) {
                        var layananDasar = ""
                        var layananPeminatanDanPerencanaanIndividual = ""
                        var layananResponsif = ""
                        var layananDukunganSistem = ""
                        var hasilLayananDasar = ""
                        var hasilLayananPeminatanDanPerencanaanIndividual = ""
                        var hasilLayananResponsif = ""
                        var hasilLayananDukunganSistem = ""

                        //Bidang Layanan ??


                        //JenisLayanan
                        if (it.child("skorLayananDasar").exists()){
                            layananDasar = "Layanan Dasar"
                        }
                        if(it.child("skorLayananPeminatanDanPerencanaanIndividual").exists()) {
                            layananPeminatanDanPerencanaanIndividual = " Layanan Peminatan Dan Perencanaan Individual"

                        }
                        if(it.child("skorLayananResponsif").exists()){
                            layananResponsif = " Layanan Responsif"
                        }
                        if(it.child("skorLayananDukunganSistem").exists()){
                            layananDukunganSistem = " Layanan Dukungan Sistem"
                        }
                        binding.tvBidangLayananSiswa.text = layananDasar + layananPeminatanDanPerencanaanIndividual + layananResponsif + layananDukunganSistem


                        //Keterangan Hasil Evaluasi
                        if (it.child("skorLayananDasar").exists()){
                            hasilLayananDasar = "Layanan Dasar: " + it.child("skorLayananDasar").value.toString()
                        }
                        if(it.child("skorLayananPeminatanDanPerencanaanIndividual").exists()) {
                            hasilLayananPeminatanDanPerencanaanIndividual = " Layanan Peminatan Dan Perencanaan Individual: " + it.child("skorLayananPeminatanDanPerencanaanIndividual").value.toString()
                        }
                        if(it.child("skorLayananResponsif").exists()){
                            hasilLayananResponsif = " Layanan Responsif: " + it.child("skorLayananResponsif").value.toString()
                        }
                        if(it.child("skorLayananDukunganSistem").exists()){
                            hasilLayananDukunganSistem =" Layanan Dukungan Sistem: " + it.child("skorLayananDukunganSistem").value.toString()
                        }

                        binding.tvKeteranganHasilEvaluasiSiswa.text = hasilLayananDasar + hasilLayananPeminatanDanPerencanaanIndividual + hasilLayananResponsif + hasilLayananDukunganSistem

                    }
                }.addOnFailureListener {
                    Log.e("firebase", "Error getting data", it)
                }
        }
        else{
            Toast.makeText(this@HomeActivity, "UserID = null", Toast.LENGTH_SHORT).show()
        }
    }
    fun encodeUserEmail(userEmail: String): String? {
        return userEmail.replace(".", ",")
    }
}