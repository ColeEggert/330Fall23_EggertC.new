package com.bignerdranch.android.geoquiz

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.bignerdranch.android.geoquiz.databinding.ActivityMainBinding

private const val TAG = "MainActivity"
private const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true),
        Question(R.string.question_amazon_river, true),
        Question(R.string.question_great_wall, false),
        Question(R.string.question_antarctica, true),
        Question(R.string.question_sahara_desert, true),
        Question(R.string.question_mount_everest, true),
        Question(R.string.question_equator, true),
        Question(R.string.question_russia, true),
        Question(R.string.question_time_zones, true),
        Question(R.string.question_pacific_ocean, true),
        Question(R.string.question_dead_sea, true),
        Question(R.string.question_nile_river, true),
        Question(R.string.question_australia_country, true),
        Question(R.string.question_prime_meridian, true),
        Question(R.string.question_andes_mountains, true),
        Question(R.string.question_largest_ocean, false),
        Question(R.string.question_uk_countries, false),
        Question(R.string.question_eiffel_tower, false),
        Question(R.string.question_mount_kilimanjaro, true),
        Question(R.string.question_great_barrier_reef, false),
        Question(R.string.question_chile_coastline, false),
    )

    private val userAnswers = BooleanArray(questionBank.size) // To store user answers
    private var answeredQuestions = mutableSetOf<Int>() // To track answered questions
    private var currentIndex = 0
    private var isCheater = false

    private val cheatLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // a lambda function to handle the result
        if (result.resultCode == Activity.RESULT_OK) {
            isCheater =
                result.data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            Log.d(TAG, "savedInstanceState is set.")
            currentIndex = savedInstanceState.getInt(CURRENT_INDEX_KEY, 0)
        }

        binding = ActivityMainBinding.inflate(this.layoutInflater)
        setContentView(binding.root)

        updateAnswerButtonsState()
        updateQuestion()

        binding.nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }

        binding.prevButton.setOnClickListener {
            currentIndex = (currentIndex - 1 + questionBank.size) % questionBank.size
            updateQuestion()
        }

        binding.questionText.setOnClickListener {
            binding.nextButton.performClick()
        }

        binding.trueButton.setOnClickListener {
            onAnswerButtonClicked(true)
        }

        binding.falseButton.setOnClickListener {
            onAnswerButtonClicked(false)
        }

        binding.cheatButton.setOnClickListener {
            // Start the cheat activity
            val intent = Intent(this, CheatActivity::class.java)
            val answer = questionBank[currentIndex].answer
            intent.putExtra(EXTRA_ANSWER_KEY, answer)
            cheatLauncher.launch(intent)
        }
    }

    private fun updateQuestion() {
        val question = questionBank[currentIndex].testResId
        binding.questionText.setText(question)
        updateAnswerButtonsState()
    }

    private fun onAnswerButtonClicked(userAnswer: Boolean) {
        if (!answeredQuestions.contains(currentIndex)) {
            checkAnswer(userAnswer)
            userAnswers[currentIndex] = userAnswer
            answeredQuestions.add(currentIndex)
            updateAnswerButtonsState()

            if (answeredQuestions.size == questionBank.size) {
                checkQuizCompletion()
            }
        }
    }


    private fun updateAnswerButtonsState() {
        val isAnswered = answeredQuestions.contains(currentIndex)
        binding.trueButton.isEnabled = !isAnswered
        binding.falseButton.isEnabled = !isAnswered

        if (isAnswered) {
            if (userAnswers[currentIndex]) {
                binding.trueButton.setBackgroundResource(R.drawable.correct_button)
                binding.falseButton.setBackgroundResource(android.R.drawable.btn_default)
            } else {
                binding.falseButton.setBackgroundResource(R.drawable.incorrect_button)
                binding.trueButton.setBackgroundResource(android.R.drawable.btn_default)
            }
        }
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer
        val resId = when {
            isCheater -> R.string.judgment_toast
            userAnswer == correctAnswer -> R.string.correct_toast
            else -> R.string.incorrect_toast
        }
        isCheater = false

        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
    }

    private fun checkQuizCompletion() {
        if (answeredQuestions.size == questionBank.size) {
            val correctAnswers = userAnswers.count { it }
            val percentCorrect = (correctAnswers.toFloat() / questionBank.size) * 100
            val message = "$correctAnswers out of ${questionBank.size} correct. %.2f%% Score".format(percentCorrect)
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    // Configuration changes.
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState")
        outState.putInt(CURRENT_INDEX_KEY, this.currentIndex)
        answeredQuestions = mutableSetOf()
        answeredQuestions.addAll(answeredQuestions)
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }
}
