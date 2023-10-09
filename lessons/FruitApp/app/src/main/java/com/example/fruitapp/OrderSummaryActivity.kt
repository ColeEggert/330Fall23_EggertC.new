package com.example.fruitapp


import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fruitapp.R


class OrderSummaryActivity : AppCompatActivity() {
    private lateinit var backButton: Button
    private lateinit var placeOrderButton: Button


    private var selectedFruit: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_summary)


        backButton = findViewById(R.id.backButton)
        placeOrderButton = findViewById(R.id.placeOrderButton)


        // Retrieve selected fruit from com.example.fruitapp.OrderFruitActivity
        selectedFruit = intent.getStringExtra("selectedFruit")


        // Handle Back button
        backButton.setOnClickListener {
            finish() // Go back to com.example.fruitapp.OrderFruitActivity
        }


        // Handle Place Order button
        placeOrderButton.setOnClickListener {
            // Display order placed message
            Toast.makeText(this@OrderSummaryActivity, "Order placed!", Toast.LENGTH_SHORT).show()
            // You may want to perform further actions here, such as sending the order to a server
        }
    }
}
