package com.kp.quiz

import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Html
import android.text.Spanned
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.kp.quiz.Data.QuizResult
import java.util.*


class SinglePlay : AppCompatActivity() {

    private lateinit var answerViews: Array<AnswerView>
    private  lateinit var questionText: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var correctTextView: TextView
    private lateinit var questionNumberTextView: TextView
    private var questionCounter = 0
    private lateinit var questionsFetcher: QuestionsFetcher
    private lateinit var timeleftBar: ProgressBar
    private lateinit var mediaPlayerError: MediaPlayer
    private lateinit var mediaPlayerCorrect: MediaPlayer
    private lateinit var timer : CountDownTimer

    private var quizResult: QuizResult = QuizResult(0,0)

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_play)
        mediaPlayerError =  MediaPlayer.create(this, R.raw.error)
        mediaPlayerCorrect = MediaPlayer.create(this, R.raw.correct_answer_sound)
        answerViews = arrayOf(
            AnswerView(findViewById(R.id.answerA_text),findViewById(R.id.answerA_button)),
            AnswerView(findViewById(R.id.answerB_text),findViewById(R.id.answerB_button)),
            AnswerView(findViewById(R.id.answerC_text),findViewById(R.id.answerC_button)),
            AnswerView(findViewById(R.id.answerD_text),findViewById(R.id.answerD_button))
            )

        correctTextView = findViewById(R.id.correctText)
        progressBar = findViewById(R.id.progressBar2)
        questionText = findViewById(R.id.question_text)
        questionNumberTextView = findViewById(R.id.questionNumber)
        timeleftBar = findViewById(R.id.timeBar)

        questionsFetcher = QuestionsFetcher(this, intent.getIntExtra(getString(R.string.select_category),0))
        questionsFetcher.onFetchSuccess = {
            displayCurrentQuestion()

            showData()
        }
        questionsFetcher.fetch()

        showLoading()
    }
    private fun displayCurrentQuestion(){

        answerViews.shuffle()
        questionText.text = decode(questionsFetcher.questions[questionCounter].question)

        answerViews[0].textView.text = decode(questionsFetcher.questions[questionCounter].correct_answer)
        answerViews[0].button.setOnClickListener() {
            answerViews[0].button.setBackgroundColor(Color.GREEN)
            answerViews[0].button.setTextColor(Color.WHITE)
            answerViews[0].button.isClickable = false
            onCorrectAnswerClicked( mediaPlayerCorrect)
        }

        for(i in 1..3){
            answerViews[i].textView.text = decode(questionsFetcher.questions[questionCounter].incorrect_answers[i-1])

            answerViews[i].button.setOnClickListener {

                answerViews[i].button.setBackgroundColor(Color.RED)
                answerViews[i].button.setTextColor(Color.WHITE)
                answerViews[i].button.isClickable = false

                onIncorrectAnswerClicked(mediaPlayerError)
            }
        }
        answerViews.forEach { answerView ->
            answerView.button.setBackgroundColor(Color.GRAY)
            answerView.button.setTextColor(Color.BLACK)

        }
        answerViews.forEach { answerView -> answerView.button.isClickable = true }
        questionNumberTextView.text = getString(R.string.question_number_prefix).plus(" ").plus(questionCounter+1).plus("/").plus(questionsFetcher.questions.size)
        setupTimer()
        //questionText.visibility = View.VISIBLE
    }
    private fun setupTimer(){
        timeleftBar.max = 10000
        timer = object: CountDownTimer(10000, 1) {
            override fun onTick(millisUntilFinished: Long) {
                timeleftBar.progress = millisUntilFinished.toInt()
            }
            override fun onFinish() {
                onIncorrectAnswerClicked(mediaPlayerError)
            }
        }
        timer.start()

    }
    private fun decode(txt: String): Spanned = Html.fromHtml(txt, Html.FROM_HTML_MODE_COMPACT)
    private fun onCorrectAnswerClicked(mediaPlayer: MediaPlayer){
        correctTextView.text = getString(R.string.correct)
        quizResult.correctAnswers += 1
        onAnswerClicked(mediaPlayer)
    }
    private fun onIncorrectAnswerClicked(mediaPlayer: MediaPlayer){
        quizResult.incorrectAnswers += 1
        correctTextView.text = getString(R.string.incorrect_answer)
        onAnswerClicked(mediaPlayer)
    }
    private fun onAnswerClicked(mediaPlayer: MediaPlayer){
        timer.cancel()
        val animation = AnimationUtils.loadAnimation(
            this, R.anim.slide_down)
        //questionText.visibility = View.GONE
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
                answerViews.forEach { answerView -> answerView.button.isClickable = false }
                answerViews[0].button.setBackgroundColor(Color.GREEN)
                answerViews[0].button.setTextColor(Color.WHITE)
            }

            override fun onAnimationRepeat(p0: Animation?) {}

            override fun onAnimationEnd(p0: Animation?) {
                nextQuestion()
            }
        })
        correctTextView.startAnimation(animation)
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.prepare()
        }
        mediaPlayer.start()
    }
    private fun nextQuestion(){
        questionCounter++
        if (questionCounter < questionsFetcher.questions.size) displayCurrentQuestion()
        else showResult()
    }

    private fun showResult(){
        val intent = Intent(this, QuizResultActivity::class.java).apply {
            putExtra(getString(R.string.correct_answer_result), quizResult.correctAnswers)
            putExtra(getString(R.string.incorrect_answer_result), quizResult.incorrectAnswers)
        }
        startActivity(intent)
    }
    private fun showLoading(){
        answerViews.forEach { answerView ->
            answerView.button.visibility = View.GONE
            answerView.textView.visibility = View.GONE
        }
        questionText.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }
    private fun showData(){
        answerViews.forEach { answerView ->
            answerView.button.visibility = View.VISIBLE
            answerView.textView.visibility = View.VISIBLE
        }
        questionText.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }
}
