package com.udemy.quizapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.udemy.quizapp.databinding.ActivityMainBinding
import com.udemy.quizapp.helper.Constants

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener{
            if(!binding.etName.text.isNullOrEmpty()){
                val intent = Intent(this, QuizQuestionsActivity::class.java)
                intent.putExtra(Constants.USER_NAME,binding.etName.text.toString())
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this,"Please enter your name",Toast.LENGTH_SHORT).show()
            }
        }
    }
}