package com.bignerdranch.android.geoquiz

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bignerdranch.android.geoquiz.databinding.ActivityCheatBinding

const val EXTRA_ANSWER_KEY = "com.bignerdranch.android.geoquiz.answer_key"
const val EXTRA_ANSWER_SHOWN = "com.bignerdranch.android.geoquiz.answer_shown"

class CheatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheatBinding
    private var answer = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        answer = intent.getBooleanExtra(EXTRA_ANSWER_KEY, false)
        binding.showAnswerButton.setOnClickListener {
            val textVal = when {
                answer -> R.string.true_button
                else -> R.string.false_button
            }
            binding.answerTextView.setText(textVal)
            val intentData = Intent().apply {
                putExtra(EXTRA_ANSWER_SHOWN, true)
            }
            setResult(Activity.RESULT_OK, intentData)
        }

        // Add a "Back" button to return to the main activity
        binding.backButton.setOnClickListener {
            finish()
        }
    }
}