package com.udemy.quizapp.view

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.udemy.quizapp.R
import com.udemy.quizapp.databinding.ActivityMainBinding
import com.udemy.quizapp.databinding.ActivityQuizQuestionsBinding
import com.udemy.quizapp.helper.Constants
import com.udemy.quizapp.model.Question

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityQuizQuestionsBinding

    private var questionList: ArrayList<Question>? = null
    private var options: ArrayList<TextView>? = null
    private var selectedOptionPosition = 0
    private var currentPosition = 1
    private var userName = ""
    private var correctAnswers = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizQuestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userName = intent.getStringExtra(Constants.USER_NAME).toString()
        questionList = Constants.getQuestions()
        saveDefaultViews()
        setQuestion()
        initListeners()
    }

    private fun initListeners() {
        binding.tvOptionOne.setOnClickListener(this)
        binding.tvOptionTwo.setOnClickListener(this)
        binding.tvOptionThree.setOnClickListener(this)
        binding.tvOptionFour.setOnClickListener(this)
        binding.btnSubmit.setOnClickListener(this)
    }

    private fun saveDefaultViews() {
        options = ArrayList<TextView>()
        options?.add(0, binding.tvOptionOne)
        options?.add(1, binding.tvOptionTwo)
        options?.add(2, binding.tvOptionThree)
        options?.add(3, binding.tvOptionFour)
    }

    private fun defaultOptionsViews() {
        for (option in options!!) {
            option.setTextColor(ContextCompat.getColor(this, R.color.gray))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }
    }

    private fun setQuestion() {
        defaultOptionsViews()
        val question: Question = questionList!![currentPosition - 1]
        val progress = "$currentPosition/${binding.pgrBar.max}"

        binding.tvQuestion.text = question.question
        binding.ivFlag.setImageResource(question.image)
        binding.pgrBar.setProgress(currentPosition)
        binding.tvProgress.text = progress
        binding.tvOptionOne.text = question.optionOne
        binding.tvOptionTwo.text = question.optionTwo
        binding.tvOptionThree.text = question.optionThree
        binding.tvOptionFour.text = question.optionFour

        if (currentPosition != questionList!!.size) {
            binding.btnSubmit.text = "SUBMIT"
        }else{
            binding.btnSubmit.text = "FINISH"
        }
    }

    private fun selectedOption(tv: TextView, selectedOptionNum: Int) {
        defaultOptionsViews()
        selectedOptionPosition = selectedOptionNum
        tv.setTextColor(ContextCompat.getColor(this, R.color.black))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            binding.tvOptionOne.id -> selectedOption(binding.tvOptionOne, 1)
            binding.tvOptionTwo.id -> selectedOption(binding.tvOptionTwo, 2)
            binding.tvOptionThree.id -> selectedOption(binding.tvOptionThree, 3)
            binding.tvOptionFour.id -> selectedOption(binding.tvOptionFour, 4)
            binding.btnSubmit.id -> onSummit()

        }
    }

    private fun onSummit() {
        if (selectedOptionPosition == 0) {
            currentPosition++
            when {
                currentPosition <= questionList!!.size -> {
                    setQuestion()
                }
                else -> {
                    val intent = Intent(this,ResultQuizActivity::class.java)
                    intent.putExtra(Constants.USER_NAME,userName)
                    intent.putExtra(Constants.CORRECT_ANSWERS,correctAnswers)
                    intent.putExtra(Constants.TOTAL_QUESTIONS,questionList!!.size)
                    startActivity(intent)
                    finish()
                }
            }
        } else {
            val question = questionList?.get(currentPosition - 1)
            if (question!!.correctAnswer != selectedOptionPosition) {
                answersView(selectedOptionPosition, R.drawable.wrong_option_border_bg)
            } else {
                correctAnswers++
            }
            answersView(question.correctAnswer, R.drawable.correct_option_border_bg)
            if (currentPosition != questionList!!.size) {
                binding.btnSubmit.text = "GO TO THE NEXT QUESTION"
            } else {
                binding.btnSubmit.text = "FINISH"
            }
            selectedOptionPosition = 0
        }
    }

    private fun answersView(answer: Int, drawableView: Int) {
        when (answer) {
            1 -> binding.tvOptionOne.background = ContextCompat.getDrawable(this, drawableView)
            2 -> binding.tvOptionTwo.background = ContextCompat.getDrawable(this, drawableView)
            3 -> binding.tvOptionThree.background = ContextCompat.getDrawable(this, drawableView)
            4 -> binding.tvOptionFour.background = ContextCompat.getDrawable(this, drawableView)
        }
    }
}