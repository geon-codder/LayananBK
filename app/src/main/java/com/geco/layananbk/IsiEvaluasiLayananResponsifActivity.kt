package com.geco.layananbk

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import com.geco.layananbk.databinding.ActivityIsiEvaluasiLayananResponsifBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class IsiEvaluasiLayananResponsifActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIsiEvaluasiLayananResponsifBinding

    private var skorLayananResponsif = 0
    private var correct = 0
    private var skip = 0
    private var qIndex = 0
    private var updateQueNo = 1


    private var questions = arrayOf(
        "1. Guru BK melakukan need assesment tentang layanan responsif?",
        "2. Guru BK menjelaskan apa tujuan layanan responsif?",
        "3. Guru BK menjelaskan tujuan pengembangan yang ingin dicapai?",
        "4. Terdapat tujuan layanan responsif dalam program BK?",
        "5. Program layanan responsif sesuai dengan need assesmen?",
        "6. Layanan responsif dilakukan sesuai dengan perkembangan peserta didik?",
        "7. Layanan responsif sesuai dengan visi dan misi sekolah?",
        "8. Layanan yang dilakukan sesuai dengan Panduan Operasional Penyelenggaraan Bimbingan dan Konseling (POP BK)?",

        // Evaluasi Input
        "1. Layanan responsif yang diberikan berdampak jelas pada perkembangan siswa?",
        "2. Strategi pemberian layanan responsif sudah sesuai dengan jumlah guru BK dan Jumlah siswa?",
        "3. Strategi layanan sesuai dengan sarana prasarana yang tersedia?",
        "4. Terdapat jadwal khusus dalam pemberian layanan  responsif?",
        "5. Terdapat media/alat yang digunakan dalam pemberian layanan responsif?",
        "6. Terdapat teknik khusus yang digunakan dalam layanan responsif?",
        "7. Layanan responsif dilakukan oleh guru BK?",


        // Evaluasi Proses
        "1. Pelaksanaan layanan sudah sesuai jadwal yang telah dibuat?",
        "2. Semua staf sekolah terlibat dalam kelancaran layanan?",
        "3. Sarana dan prasarana yang disediakan dapat dimanfaatkan secara maksimal?",
        "4. Terdapat hambatan dalam pelaksanaan layanan?",

        //Evaluasi Produk
        "1. Tujuan layanan telah tercapai sesuai dengan apa yang ditetapkan?",
        "2. Terdapat hubungan antara prosedur layanan dengan  hasil layanan?",
        "3. Kebutuhan siswa telah terpenuhi dengan layanan responsif?",
        "4. Terdapat hasil jangka panjang dari layanan responsif?"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIsiEvaluasiLayananResponsifBinding.inflate(layoutInflater)
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
            // if selected then selected option correct or wrong
            nextQuestionBtn.setOnClickListener {
                if (radiogrp.checkedRadioButtonId == -1) {
                    Toast.makeText(this@IsiEvaluasiLayananResponsifActivity,
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
                skorLayananResponsif = correct
                //TODO tentukan kategori berdasarkan skornya

                val database = FirebaseDatabase.getInstance()
                val myRef = database.getReference("dataUser")
                val getUserEmail = Firebase.auth.currentUser?.email.toString()
                myRef.child("users").child(encodeUserEmail(getUserEmail).toString()).child("Layanan")
                    .child("Isi Evaluasi").child("skorLayananResponsif").setValue(skorLayananResponsif)

                val intent = Intent(this@IsiEvaluasiLayananResponsifActivity, IsiEvaluasiActivity::class.java)
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
                        correct += 4
                    }
                    "Setuju" -> {
                        correct += 3
                    }
                    "Tidak Setuju" -> {
                        correct += 2
                    }
                    "Sangat Tidak Setuju" -> {
                        correct += 1
                    }
                }
            }
            qIndex++
        }
    }
}