package com.kp.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.kp.quiz.Data.QuizResult

class QuizResultActivity : AppCompatActivity() {

    private lateinit var tryAgainButton: Button
    private lateinit var correctAnswersTextView: TextView
    private lateinit var incorrectAnswersView: TextView

    private lateinit var result: QuizResult

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_result)

        tryAgainButton = findViewById(R.id.back_to_categories)
        correctAnswersTextView = findViewById(R.id.correctAnswersText)
        incorrectAnswersView = findViewById(R.id.incorrectAnswersText)

        result = QuizResult(
            intent.getIntExtra(getString(R.string.correct_answer_result),0),
            intent.getIntExtra(getString(R.string.incorrect_answer_result),0)
            )

        correctAnswersTextView.text = "Correct Answers: ${result.correctAnswers}"
        incorrectAnswersView.text = "Incorrect Answers: ${result.incorrectAnswers}"
        tryAgainButton.setOnClickListener {
            val intent = Intent(this, CategoryMenu::class.java).apply {
                putExtra(getString(R.string.game_mode_msg), 1)
            }
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
