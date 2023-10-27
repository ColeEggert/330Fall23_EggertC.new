package com.bignerdranch.android.geoquiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.bignerdranch.android.geoquiz.R

class MainFragment : Fragment() {

    private lateinit var startQuizButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        // Initialize UI elements
        startQuizButton = view.findViewById(R.id.startQuizButton)

        // Set click listener for the button
        startQuizButton.setOnClickListener {
            // Handle button click, e.g., start the quiz.
        }

        return view
    }
}
