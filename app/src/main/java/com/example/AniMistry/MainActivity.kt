package com.example.AniMistry

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.widget.Toast



import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var animalImageView: ImageView
    private lateinit var animalNameEditText: EditText
    private lateinit var submitButton: Button
    private lateinit var resetScore: Button
    private lateinit var nextButton: Button
    private lateinit var scoreText: TextView
    private lateinit var dbHelper: DatabaseHelper
    private var score = 0

    private val animalImages = listOf(
        R.drawable.lion,
        R.drawable.tiger
    )
    private val animalNames = listOf("lion", "tiger", "elephant", "dog", "cat")
    private var currentAnimalIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DatabaseHelper(this)

        animalImageView = findViewById(R.id.animalImageView)
        animalNameEditText = findViewById(R.id.animalNameEditText)
        submitButton = findViewById(R.id.submitButton)
        nextButton = findViewById(R.id.nextButton)
        scoreText = findViewById(R.id.scoreText)
        resetScore = findViewById(R.id.resetScore)

        // Display the first animal
        displayAnimal()

        resetScore.setOnClickListener{
            resetScore();
        }

        submitButton.setOnClickListener {
            val userInput = animalNameEditText.text.toString().trim().toLowerCase()
            val correctAnswer = animalNames[currentAnimalIndex]

            if (userInput == correctAnswer) {
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
                score++
                scoreText.text = "Score: $score"


                //val result = dbHelper.insertUser("Harsha ", score);
                val result = dbHelper.updateScore("Harsha", score)
                if (result > 0) {
                    Toast.makeText(this, "Score Updated successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Score Updated failed", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(this, "Try again!", Toast.LENGTH_SHORT).show()
            }
        }

        nextButton.setOnClickListener {
            currentAnimalIndex = (currentAnimalIndex + 1) % animalImages.size
            displayAnimal()
            animalNameEditText.text.clear()
        }
    }

    private fun resetScore(){
        score = 0
        scoreText.text = "Score: 0"
        val result = dbHelper.updateScore("Harsha", score)
        if (result > 0) {
            Toast.makeText(this, "Score Updated successfully", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Score Updated failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayAnimal() {
        animalImageView.setImageResource(animalImages[currentAnimalIndex])
        val retrievedScore = dbHelper.getAnimalByName("Harsha")
        scoreText.text = "Previous Score: $retrievedScore"
    }
}
