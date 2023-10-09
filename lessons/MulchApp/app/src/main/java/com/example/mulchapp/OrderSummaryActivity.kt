package com.example.mulchapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class OrderSummaryActivity() : AppCompatActivity(), Parcelable {

    private lateinit var mulchType: String
    private lateinit var cubicYards: String
    private lateinit var deliveryAddress: String
    private lateinit var email: String
    private lateinit var phone: String
    private lateinit var mulchCost: String
    private lateinit var salesTax: String
    private lateinit var deliveryCharge: String
    private lateinit var totalCost: String
    private lateinit var placeOrderButton: Button

    constructor(parcel: Parcel) : this() {
        mulchType = parcel.readString().toString()
        cubicYards = parcel.readString().toString()
        deliveryAddress = parcel.readString().toString()
        email = parcel.readString().toString()
        phone = parcel.readString().toString()
        mulchCost = parcel.readString().toString()
        salesTax = parcel.readString().toString()
        deliveryCharge = parcel.readString().toString()
        totalCost = parcel.readString().toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_summary)

        mulchType = intent.getStringExtra("selectedMulchType") ?: ""
        cubicYards = intent.getStringExtra("cubicYards") ?: ""
        deliveryAddress = intent.getStringExtra("deliveryAddress") ?: ""
        email = intent.getStringExtra("email") ?: ""
        phone = intent.getStringExtra("phone") ?: ""
        mulchCost = intent.getStringExtra("mulchCost") ?: ""
        salesTax = intent.getStringExtra("salesTax") ?: ""
        deliveryCharge = intent.getStringExtra("deliveryCharge") ?: ""
        totalCost = intent.getStringExtra("totalCost") ?: ""

        val summaryTextView = findViewById<TextView>(R.id.summaryTextView)
        val orderDetailsTextView = findViewById<TextView>(R.id.orderDetailsTextView)

        val orderSummaryText = "Order summary:\n\nDelivering $cubicYards cubic yards of $mulchType to:\n$deliveryAddress\nEmail: $email\nPhone: $phone\n\nOrder Details:\nMulch Cost: $mulchCost\nSales Tax: $salesTax\nDelivery Charge: $deliveryCharge\nTotal Cost: $totalCost"

        summaryTextView.text = orderSummaryText

        placeOrderButton = findViewById(R.id.placeOrderButton)

        placeOrderButton.setOnClickListener {

            val confirmationMessage = "Order placed successfully!"
            Toast.makeText(this@OrderSummaryActivity, confirmationMessage, Toast.LENGTH_SHORT).show()
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(mulchType)
        parcel.writeString(cubicYards)
        parcel.writeString(deliveryAddress)
        parcel.writeString(email)
        parcel.writeString(phone)
        parcel.writeString(mulchCost)
        parcel.writeString(salesTax)
        parcel.writeString(deliveryCharge)
        parcel.writeString(totalCost)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderSummaryActivity> {
        override fun createFromParcel(parcel: Parcel): OrderSummaryActivity {
            return OrderSummaryActivity(parcel)
        }

        override fun newArray(size: Int): Array<OrderSummaryActivity?> {
            return arrayOfNulls(size)
        }
    }
}
