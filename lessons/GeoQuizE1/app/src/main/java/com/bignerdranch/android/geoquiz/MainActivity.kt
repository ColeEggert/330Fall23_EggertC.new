package com.bignerdranch.android.geoquiz

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.bignerdranch.android.geoquiz.databinding.ActivityMainBinding

private const val TAG = "MainActivity"
private const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val answeredQuestions = mutableSetOf<Int>()
    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true),
        Question(R.string.question_amazon_river, true),
        Question(R.string.question_great_wall_of_china, false),
        Question(R.string.question_antarctica_driest_continent, true),
        Question(R.string.question_sahara_desert_largest_hot_desert, true),
        Question(R.string.question_mount_everest_tallest_mountain, true),
        Question(R.string.question_equator_passes_through_african_countries, true),
        Question(R.string.question_russia_spans_two_continents, true),
        Question(R.string.question_us_largest_number_of_time_zones, true),
        Question(R.string.question_pacific_ocean_largest_and_deepest, true),
        Question(R.string.question_dead_sea_saltwater_lake, true),
        Question(R.string.question_nile_river_longest_river, true),
        Question(R.string.question_australia_country_and_continent, true),
        Question(R.string.question_prime_meridian_runs_through_greenwich, true),
        Question(R.string.question_andes_mountains_longest_range, true),
        Question(R.string.question_largest_ocean_surface_area, false),
        Question(R.string.question_uk_includes_england_scotland_wales_not_northern_ireland, false),
        Question(R.string.question_eiffel_tower_located_in_paris, true),
        Question(R.string.question_mount_kilimanjaro_tallest_mountain_in_africa, true),
        Question(R.string.question_great_barrier_reef_worlds_largest_coral_reef_in_australia, false),
        Question(R.string.question_chile_has_coastline_along_pacific_not_atlantic, true)
    )
    private val userAnswers = BooleanArray(questionBank.size)
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

        binding.questionText.setText(questionBank[currentIndex].testResId)

        // Add the listener for the question TextView
        binding.questionText.setOnClickListener {
            binding.nextButton.performClick()
        }


        binding.nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            binding.questionText.setText(questionBank[currentIndex].testResId)
        }

        binding.cheatButton.setOnClickListener {
            // Start the cheat activity
            val intent = Intent(this, CheatActivity::class.java)
            val answer = questionBank[currentIndex].answer
            intent.putExtra(EXTRA_ANSWER_KEY, answer)
            cheatLauncher.launch(intent)
        }

        // Find the "PREV" Button by its ID
        val prevButton = findViewById<Button>(R.id.prev_button)

        prevButton.setOnClickListener {
            currentIndex = (currentIndex - 1 + questionBank.size) % questionBank.size
            updateQuestion()
        }


        binding.trueButton.setOnClickListener {
            onAnswerButtonClicked(true)
        }

        binding.falseButton.setOnClickListener {
            onAnswerButtonClicked(false)
        }




        // Set up the NavController for MainActivity
        val navController = findNavController(R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    private fun updateQuestion() {
        val question = questionBank[currentIndex].testResId
        binding.questionText.setText(question)
    }

    private fun onAnswerButtonClicked(userAnswer: Boolean) {
        if (!answeredQuestions.contains(currentIndex)) {
            checkAnswer(userAnswer)
            userAnswers[currentIndex] = userAnswer // Store user's answer
            answeredQuestions.add(currentIndex)
            updateAnswerButtonsState()
        }
        updateButtonDrawables(userAnswer)
    }

    private fun updateButtonDrawables(userAnswer: Boolean) {
        if (userAnswer) {
            binding.trueButton.setBackgroundResource(R.drawable.correct_button)
            binding.falseButton.setBackgroundResource(R.drawable.incorrect_button)
        } else {
            binding.trueButton.setBackgroundResource(R.drawable.incorrect_button)
            binding.falseButton.setBackgroundResource(R.drawable.correct_button)
        }
    }

    private fun updateAnswerButtonsState() {
        val isAnswered = answeredQuestions.contains(currentIndex)
        binding.trueButton.isEnabled = !isAnswered
        binding.falseButton.isEnabled = !isAnswered

        // Display user's answer if it exists
        if (isAnswered) {
            if (userAnswers[currentIndex]) {
                binding.trueButton.setBackgroundResource(R.drawable.correct_button)
            } else {
                binding.falseButton.setBackgroundResource(R.drawable.incorrect_button)
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

    private fun gradeQuiz() {
        val correctAnswers = answeredQuestions.count { questionBank[it].answer }
        val totalQuestions = questionBank.size
        val percentCorrect = (correctAnswers.toFloat() / totalQuestions) * 100
        val message = "$correctAnswers out of $totalQuestions correct. %.2f%% Score".format(percentCorrect)
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    // Handle Up (back) navigation
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return NavigationUI.navigateUp(navController, null)
    }

    // Configuration changes.
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState")
        outState.putInt(CURRENT_INDEX_KEY, this.currentIndex)
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
