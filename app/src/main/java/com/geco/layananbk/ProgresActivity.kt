package com.geco.layananbk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

                // Add a listener to the DatabaseReference
                myRef.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val skorEvaluasiUmum = dataSnapshot.child("Evaluasi Umum").child("skorEvaluasiUmum").value
                        val skorLayananDasar = dataSnapshot.child("Isi Evaluasi Layanan Dasar").child("skorLayananDasar").value
                        mutableList.add(skorEvaluasiUmum.toString())
                        mutableList.add(skorLayananDasar.toString())
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
                        labels: ["Data 1", "Data 2", "Data 3"],
                        datasets: [{
                            label: "Skor",
                            data: ${mutableList},
                            backgroundColor: ["#ff0000", "#00ff00", "#0000ff"]
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
//                        htmlContentBaru = htmlContentBaru.replace("10", "$skorEvaluasiUmum")
//                        htmlContentBaru = htmlContentBaru.replace("20", "$skorLayananDasar")
                        webView.loadDataWithBaseURL("https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.min.js", htmlContentBaru, "text/html", "UTF-8",null)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Handle the error
                    }

                })
//                Toast.makeText(this@ProgresActivity, "$mutableList", Toast.LENGTH_SHORT).show()
//                Toast.makeText(this@ProgresActivity, "Belum Ada Tugas di selesaikan", Toast.LENGTH_SHORT).show()
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