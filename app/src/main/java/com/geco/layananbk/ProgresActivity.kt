package com.geco.layananbk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.geco.layananbk.databinding.ActivityProgresBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class ProgresActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProgresBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProgresBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Load chart from cloudflare.com


        binding.apply {

            // Load file chart.html
            val htmlContentBaru = resources.openRawResource(R.raw.html_contenctbaru).bufferedReader().use { it.readText() }
            webView.settings.javaScriptEnabled = true
            webView.loadDataWithBaseURL("https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.min.js", htmlContentBaru, "text/html", "UTF-8",null)

            btnPeriksaHasil.setOnClickListener {
                val database = FirebaseDatabase.getInstance()
                val getUserEmail = Firebase.auth.currentUser?.email.toString()
                val myRef = database.getReference("dataUser").child("users").child(encodeUserEmail(getUserEmail).toString()).child("Layanan")
                // Create a mutable list
                val mutableList = mutableListOf<String>()
//               /*
//Evaluasi Umum Layanan Dasar
//Evaluasi Umum Layanan Dukungan Sistem
//Evaluasi Umum Layanan Peminatan dan Perancanaan Individual
//Evaluasi Umum Layanan Responsif
//EvaluasiProses
//Isi Evaluasi Layanan Dasar
//Isi Evaluasi Layanan Dukungan Sistem
//Isi Evaluasi Layanan Peminatan
//Isi Evaluasi Layanan Responsif
//
//               */


                // Add a listener to the DatabaseReference
                myRef.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val skorEvaluasiUmumLayananDasar = dataSnapshot.child("Evaluasi Umum Layanan Dasar").child("skorLayananDasar").value
                        val skorEvaluasiUmumLayananDukunganSistem = dataSnapshot.child("Evaluasi Umum Layanan Dukungan Sistem").child("skorLayananDukunganSistem").value
                        val skorEvaluasiUmumLayananPeminatandanPerancanaanIndividual = dataSnapshot.child("Evaluasi Umum Layanan Peminatan dan Perancanaan Individual").child("skorLayananPeminatandanPerencanaanIndividual").value
                        val skorEvaluasiUmumLayananResponsif = dataSnapshot.child("Evaluasi Umum Layanan Responsif").child("skorLayananResponsif").value
                        val skorEvaluasiProses = dataSnapshot.child("EvaluasiProses").child("skorEvaluasiProses").value
                        val skorLayananDasar = dataSnapshot.child("Isi Evaluasi Layanan Dasar").child("skorLayananDasar").value
                        val skorLayananDukunganSistem = dataSnapshot.child("Isi Evaluasi Layanan Dukungan Sistem").child("skorLayananDukunganSistem").value
                        val skorLayananPeminatan = dataSnapshot.child("Isi Evaluasi Layanan Peminatan").child("skorLayananPeminatanDanPerencanaanIndividual").value
                        val skorLayananResponsif = dataSnapshot.child("Isi Evaluasi Layanan Responsif").child("skorLayananResponsif").value
                        mutableList.add(skorEvaluasiUmumLayananDasar.toString())
                        mutableList.add(skorEvaluasiUmumLayananDukunganSistem.toString())
                        mutableList.add(skorEvaluasiUmumLayananPeminatandanPerancanaanIndividual.toString())
                        mutableList.add(skorEvaluasiUmumLayananResponsif.toString())
                        mutableList.add(skorEvaluasiProses.toString())
                        mutableList.add(skorLayananDasar.toString())
                        mutableList.add(skorLayananDukunganSistem.toString())
                        mutableList.add(skorLayananPeminatan.toString())
                        mutableList.add(skorLayananResponsif.toString())
                        Toast.makeText(this@ProgresActivity, "$mutableList", Toast.LENGTH_SHORT).show()
                        var htmlContentBaru = """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <title>Data Chart</title>
            </head>
            <body>
                <canvas id="myChart"></canvas>

                <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.min.js"></script>

                <script>
                    var ctx = document.getElementById("myChart").getContext("2d");

                    var data = {
                        labels: ["EULD", "EULDS", "EULP", "EULR", "EP", "EHLD","EHLDS","EHLP", "EHLR"],
                        datasets: [{
                            label: "Skor",
                            data: ${mutableList},
                            backgroundColor: ["#F0F0F0", "#D3D3D3", "#BDBDBD", "#A6A6A6", "#909090", "#7A7A7A", "#636363", "#4D4D4D"]
                        }]
                    };

                    var myChart = new Chart(ctx, {
                        type: "bar",
                        data: data,
                        options: {
                            title: {
                                text: "Data Chart"
                            }
                        }
                    });
                    // Add the mousemove listener
                    myChart.addEventListener("mousemove", function(e) {
                    // Get the mouse position
                    var x = e.offsetX;

                    // Set the chart's translateX property
                    myChart.options.scales.xAxes[0].translateX = x;
                    });
                </script>
            </body>
            </html>
        """
                        webView.loadDataWithBaseURL("https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.min.js", htmlContentBaru, "text/html", "UTF-8",null)

                        val textKegiatanDiselesaikan = mutableListOf<String>()

                        if (skorEvaluasiUmumLayananDasar != null) {
                            textKegiatanDiselesaikan.add("Evaluasi Umum Layanan Dasar")
                        }
                        if (skorEvaluasiUmumLayananDukunganSistem != null) {
                            textKegiatanDiselesaikan.add("Evaluasi Umum Layanan Dukungan Sistem")
                        }
                        if (skorEvaluasiUmumLayananPeminatandanPerancanaanIndividual != null) {
                            textKegiatanDiselesaikan.add("Evaluasi Umum Layanan Peminatan dan Perancanaan Individual")
                        }
                        if (skorEvaluasiUmumLayananResponsif != null) {
                            textKegiatanDiselesaikan.add("Evaluasi Umum Layanan Responsif")
                        }
                        if (skorEvaluasiProses != null) {
                            textKegiatanDiselesaikan.add("Evaluasi Proses")
                        }
                        if (skorLayananDasar != null) {
                            textKegiatanDiselesaikan.add("Evaluasi Layanan Dasar")
                        }
                        if (skorLayananDukunganSistem != null) {
                            textKegiatanDiselesaikan.add("Evaluasi Layanan Dukungan Sistem")
                        }
                        if (skorLayananPeminatan != null) {
                            textKegiatanDiselesaikan.add("Evaluasi Layanan Peminatan dan Perancanaan Individual")
                        }
                        if (skorLayananResponsif != null) {
                            textKegiatanDiselesaikan.add("Evaluasi Layanan Responsif")
                        }

                        kegiatanDiselesaikan.text = textKegiatanDiselesaikan.joinToString(", ")
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Handle the error
                    }
                })
            }
            btnProgresSelesai.setOnClickListener {

                val intent = Intent(this@ProgresActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
    fun encodeUserEmail(userEmail: String): String? {
        return userEmail.replace(".", ",")
    }
}