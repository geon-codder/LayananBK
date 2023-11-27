package com.geco.layananbk

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import com.geco.layananbk.databinding.ActivityIsiEvaluasiLayananDukunganSistemBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class IsiEvaluasiLayananDukunganSistemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIsiEvaluasiLayananDukunganSistemBinding
    private var skorLayananDukunganSistem = 0
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
        binding = ActivityIsiEvaluasiLayananDukunganSistemBinding.inflate(layoutInflater)
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
                    Toast.makeText(this@IsiEvaluasiLayananDukunganSistemActivity,
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
                val jawabanLDSno1 = inputJawabanLDSno1.text.toString()
                val jawabanLDSno2 = inputJawabanLDSno2.text.toString()
                val jawabanLDSno3 = inputJawabanLDSno3.text.toString()
                val jawabanLDSno4 = inputJawabanLDSno4.text.toString()

                skorLayananDukunganSistem = skor
                //TODO tentukan kategori berdasarkan skornya

                val database = FirebaseDatabase.getInstance()
                val myRef = database.getReference("dataUser")
                val getUserEmail = Firebase.auth.currentUser?.email.toString()
                myRef.child("users").child(encodeUserEmail(getUserEmail).toString()).child("Layanan")
                    .child("Isi Evaluasi Layanan Dukungan Sistem").child("jawabanLDSno1").setValue(jawabanLDSno1)
                myRef.child("users").child(encodeUserEmail(getUserEmail).toString()).child("Layanan")
                    .child("Isi Evaluasi Layanan Dukungan Sistem").child("jawabanLDSno2").setValue(jawabanLDSno2)
                myRef.child("users").child(encodeUserEmail(getUserEmail).toString()).child("Layanan")
                    .child("Isi Evaluasi Layanan Dukungan Sistem").child("jawabanLDSno3").setValue(jawabanLDSno3)
                myRef.child("users").child(encodeUserEmail(getUserEmail).toString()).child("Layanan")
                    .child("Isi Evaluasi Layanan Dukungan Sistem").child("jawabanLDSno4").setValue(jawabanLDSno4)
                myRef.child("users").child(encodeUserEmail(getUserEmail).toString()).child("Layanan")
                    .child("Isi Evaluasi Layanan Dukungan Sistem").child("skorLayananDukunganSistem").setValue(skorLayananDukunganSistem)


                val intent = Intent(this@IsiEvaluasiLayananDukunganSistemActivity, IsiEvaluasiActivity::class.java)
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