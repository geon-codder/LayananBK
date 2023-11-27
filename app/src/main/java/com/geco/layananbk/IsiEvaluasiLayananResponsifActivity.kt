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
    private var skor = 0
    private var skip = 0
    private var qIndex = 0


    private var questions = arrayOf(
        "1. Saya memahami dengan baik tujuan yang diharapkan dari materi yang disampaikan",
        "2. Suasana dalam layanan aktif dan menyenangkan",
        "3. Saya mampu menjelaskan kembali materi layanan yang diberikan",
        "4. Saya memperoleh banyak pengetahuan dan informasi dari materi yang disampaikan",
        "5. Saya menyadari pentingnya bersikap sesuai dengan materi yang disampaikan",
        "6. Saya meyakini diri akan lebih baik, apabila bersikap sesuai dengan materi yang disampaikan",
        "7. Saya dapat mengembangkan perilaku yang lebih positif setelah mendapatkan materi yang disampaikan",
        "8. Saya dapat mengubah perilaku sehingga kehidupan saya menjadi lebih teratur dan bermakna",
        "9. Saya terbantu dalam penyelesaian masalah saya",
        "10. Saya mendapat pemahaman positif setelah mengikuti layanan",
        "11. Metode layanan yang digunakan membantu saya memahami alur dan materi layanan",
        "12. Saya terbantu dalam merencanakan pengembangan keterampilan diri",
        "13. Saya menjadi yakin bahwa saya mampu mengembangkan potensi/mengentaskan masalah",
        "14. Saya memahami hal-hal penting untuk dipertimbangkan dalam mengambil keputusan",
        "15. Saya menyadari pentingnya bersikap mandiri dalam setiap situasi"

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
            radioButton1.text = "Sangat Baik"
            radioButton2.text = "Baik"
            radioButton3.text = "Cukup baik"
            radioButton4.text = "Kurang baik"

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
            if (qIndex <= questions.size - 1) {
                tvQuestion.text = questions[qIndex]
            } else {
                //kalau soal sudah selesai semua
                val jawabanLRno1 = inputJawabanLRno1.text.toString()
                val jawabanLRno2 = inputJawabanLRno2.text.toString()
                val jawabanLRno3 = inputJawabanLRno3.text.toString()
                val jawabanLRno4 = inputJawabanLRno4.text.toString()
                
                skorLayananResponsif = skor
                //TODO tentukan kategori berdasarkan skornya

                val database = FirebaseDatabase.getInstance()
                val myRef = database.getReference("dataUser")
                val getUserEmail = Firebase.auth.currentUser?.email.toString()
                myRef.child("users").child(encodeUserEmail(getUserEmail).toString()).child("Layanan")
                    .child("Isi Evaluasi Layanan Responsif").child("jawabanLRno1").setValue(jawabanLRno1)
                myRef.child("users").child(encodeUserEmail(getUserEmail).toString()).child("Layanan")
                    .child("Isi Evaluasi Layanan Responsif").child("jawabanLRno2").setValue(jawabanLRno2)
                myRef.child("users").child(encodeUserEmail(getUserEmail).toString()).child("Layanan")
                    .child("Isi Evaluasi Layanan Responsif").child("jawabanLRno3").setValue(jawabanLRno3)
                myRef.child("users").child(encodeUserEmail(getUserEmail).toString()).child("Layanan")
                    .child("Isi Evaluasi Layanan Responsif").child("jawabanLRno4").setValue(jawabanLRno4)
                myRef.child("users").child(encodeUserEmail(getUserEmail).toString()).child("Layanan")
                    .child("Isi Evaluasi Layanan Responsif").child("skorLayananResponsif").setValue(skorLayananResponsif)

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
                    "Sangat Baik" -> {
                        skor += 4
                    }
                    "Baik" -> {
                        skor += 3
                    }
                    "Cukup baik" -> {
                        skor += 2
                    }
                    "Kurang baik" -> {
                        skor += 1
                    }
                }
            }
            qIndex++
        }
    }
}