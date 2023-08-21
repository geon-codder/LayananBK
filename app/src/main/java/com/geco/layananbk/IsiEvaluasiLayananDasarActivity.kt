package com.geco.layananbk

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.RadioButton
import android.widget.Toast
import com.geco.layananbk.databinding.ActivityIsiEvaluasiLayananDasarBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class IsiEvaluasiLayananDasarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIsiEvaluasiLayananDasarBinding

    private var skorLayananDasar = 0
    private var skor = 0
    private var skip = 0
    private var qIndex = 0


    private var questions = arrayOf(
        "1. Guru BK melakukan need assesment tentang layanan dasar?",
        "2. Guru BK menjelaskan apa tujuan layanan dasar?",
        "3. Guru BK menjelaskan tujuan pengembangan yang ingin dicapai?",
        "4. Terdapat tujuan layanan dasar dalam program BK?",
        "5. Program layanan dasar sesuai dengan need assesmen?",
        "6. Layanan dasar dilakukan sesuai dengan perkembangan peserta didik?",
        "7. Layanan dasar sesuai dengan visi dan misi sekolah?",
        "8. Layanan yang dilakukan sesuai dengan Panduan Operasional Penyelenggaraan Bimbingan dan Konseling (POP BK)?",

        // Evaluasi Input
        "1. Layanan dasar yang diberikan berdampak jelas pada perkembangan siswa?",
        "2. Strategi pemberian layanan dasar sudah sesuai dengan jumlah guru BK dan Jumlah siswa?",
        "3. Strategi layanan sesuai dengan sarana prasarana yang tersedia?",
        "4. Terdapat jadwal khusus dalam pemberian layanan dasar?",
        "5. Terdapat media/alat yang digunakan dalam pemberian layanan dasar?",
        "6. Terdapat teknik khusus  yang digunakan dalam layanan dasar?",
        "7. Layanan dasar dilakukan oleh guru BK?",

        // Evaluasi Proses
        "1. Pelaksanaan layanan sudah sesuai jadwal yang telah dibuat?",
        "2. Semua staf sekolah terlibat dalam kelancaran layanan?",
        "3. Sarana dan prasarana yang disediakan dapat dimanfaatkan secara maksimal?",
        "4. Terdapat hambatan dalam pelaksanaan layanan?",

        //Evaluasi Produk
        "1. Tujuan layanan telah tercapai sesuai dengan apa yang ditetapkan?",
        "2. Terdapat hubungan antara prosedur layanan dengan hasil layanan?",
        "3. Kebutuhan siswa telah terpenuhi dengan layanan dasar?",
        "4. Terdapat hasil jangka panjang dari layanan dasar?"
        )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIsiEvaluasiLayananDasarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    @SuppressLint("SetTextI18n")
    private fun initViews() {
        binding.apply {
            tvQuestion.text = questions[qIndex]
            radioButton1.text = "Sangat Setuju"
            radioButton2.text = "Setuju"
            radioButton3.text = "Tidak Setuju"
            radioButton4.text = "Sangat Tidak Setuju"

            // check options selected or not
            // if selected then selected option skor or wrong
            nextQuestionBtn.setOnClickListener {
                if (radiogrp.checkedRadioButtonId == -1) {
                    Toast.makeText(this@IsiEvaluasiLayananDasarActivity,
                        "Please select an options",
                        Toast.LENGTH_SHORT)
                        .show()
                } else {
                    showNextQuestion()
                }
            }
            tvQuestion.text = questions[qIndex]
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showNextQuestion() {
        checkAnswer()
        binding.apply {
            
            if (qIndex == 8){
                tvTitle.text = "Evaluasi Input"
            }else if (qIndex == 15){
                tvTitle.text = "Evaluasi Proses"
            }else if (qIndex == 19){
                tvTitle.text = "Evaluasi Produk"
            }
            if (qIndex <= questions.size - 1) {
                tvQuestion.text = questions[qIndex]
            } else {

                //kalau soal sudah selesai semua
                skorLayananDasar = skor
                //TODO tentukan kategori berdasarkan skornya

                val database = FirebaseDatabase.getInstance()
                val myRef = database.getReference("dataUser")
                val getUserEmail = Firebase.auth.currentUser?.email.toString()
                myRef.child("users").child(encodeUserEmail(getUserEmail).toString()).child("Layanan")
                    .child("Isi Evaluasi").child("skorLayananDasar").setValue(skorLayananDasar)

                val intent = Intent(this@IsiEvaluasiLayananDasarActivity, IsiEvaluasiActivity::class.java)
                startActivity(intent)
                finish()
            }
            radiogrp.clearCheck()
        }
    }

    private fun encodeUserEmail(userEmail: String): String? {
        return userEmail.replace(".", ",")
    }

    @SuppressLint("SetTextI18n")
    private fun checkAnswer() {
        binding.apply {
            if (radiogrp.checkedRadioButtonId == -1) {
                skip++
            } else {
                val checkRadioButton =
                    findViewById<RadioButton>(radiogrp.checkedRadioButtonId)
                val checkAnswer = checkRadioButton.text.toString()
                when (checkAnswer) {
                    "Sangat Setuju" -> {
                        skor += 4
                    }
                    "Setuju" -> {
                        skor += 3
                    }
                    "Tidak Setuju" -> {
                        skor += 2
                    }
                    "Sangat Tidak Setuju" -> {
                        skor += 1
                    }
                }
            }
            qIndex++
        }
    }
}