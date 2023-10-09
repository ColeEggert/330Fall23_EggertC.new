package com.example.fruitapp


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class OrderFruitActivity : AppCompatActivity() {
    private lateinit var poundsEditText: EditText
    private lateinit var zipCodeEditText: EditText
    private lateinit var backButton: Button
    private lateinit var nextButton: Button


    private var selectedFruit: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_fruit)


        poundsEditText = findViewById(R.id.poundsEditText)
        zipCodeEditText = findViewById(R.id.zipCodeEditText)
        backButton = findViewById(R.id.backButton)
        nextButton = findViewById(R.id.nextButton)


        // Retrieve selected fruit from com.example.fruitapp.MainActivity
        selectedFruit = intent.getStringExtra("selectedFruit")


        // Handle Back button
        backButton.setOnClickListener {
            finish() // Go back to com.example.fruitapp.MainActivity
        }


        // Handle Next button
        nextButton.setOnClickListener {
            // Validate pounds and ZIP code input
            // If valid, calculate costs and start com.example.fruitapp.OrderSummaryActivity
            // Otherwise, show appropriate messages to the user
            // ...


            // Start com.example.fruitapp.OrderSummaryActivity
            val intent = Intent(this@OrderFruitActivity, OrderSummaryActivity::class.java)
            intent.putExtra("selectedFruit", selectedFruit)
            // Add other necessary data for the order summary
            startActivity(intent)
        }
    }
}
