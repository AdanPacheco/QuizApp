package com.udemy.quizapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract
import com.udemy.quizapp.databinding.ActivityResultQuizBinding
import com.udemy.quizapp.helper.Constants

class ResultQuizActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultQuizBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userName = intent.getStringExtra(Constants.USER_NAME).toString()
        val correctAnswers = intent.getIntExtra(Constants.CORRECT_ANSWERS, 0)
        val totalQuestions = intent.getIntExtra(Constants.TOTAL_QUESTIONS, 0)

        val scoreText = "Your Score is $correctAnswers out of $totalQuestions"

        binding.tvName.text = userName
        binding.tvScore.text = scoreText

        binding.btnFinish.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}