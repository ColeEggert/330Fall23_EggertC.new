package com.bignerdranch.android.geoquiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.bignerdranch.android.geoquiz.R

class CheatFragment : Fragment() {

    private lateinit var showAnswerButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cheat, container, false)

        // Initialize UI elements
        showAnswerButton = view.findViewById(R.id.showAnswerButton)

        // Set click listener for the button
        showAnswerButton.setOnClickListener {
            // Handle button click, e.g., reveal the answer.
        }

        return view
    }
}
