package com.example.fruitapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private lateinit var fruitRadioGroup: RadioGroup
    private lateinit var nextButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        fruitRadioGroup = findViewById(R.id.fruitRadioGroup)
        nextButton = findViewById(R.id.nextButton)


        nextButton.setOnClickListener {
            val selectedId = fruitRadioGroup.checkedRadioButtonId
            if (selectedId != -1) {
                val selectedRadioButton = findViewById<RadioButton>(selectedId)
                val selectedFruit = selectedRadioButton.text.toString()


                // Start com.example.fruitapp.OrderFruitActivity with selected fruit
                val intent = Intent(this@MainActivity, OrderFruitActivity::class.java)
                intent.putExtra("selectedFruit", selectedFruit)
                startActivity(intent)
            } else {
                // Inform the user to select a fruit
                Toast.makeText(this@MainActivity, "Please select a fruit", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

