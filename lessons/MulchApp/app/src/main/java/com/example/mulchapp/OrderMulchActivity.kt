package com.example.mulchapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class OrderMulchActivity() : AppCompatActivity(), Parcelable {

    private lateinit var selectedMulchType: String
    private lateinit var cubicYardsInput: EditText
    private lateinit var streetInput: EditText
    private lateinit var cityInput: EditText
    private lateinit var stateInput: EditText
    private lateinit var zipCodeInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var phoneInput: EditText
    private lateinit var nextButton: Button

    private val SALES_TAX_RATE = 0.08

    private val DELIVERY_CHARGES = mapOf(
        "60540" to 25.0,
        "60563" to 30.0,
        "60564" to 35.0,
        "60565" to 35.0,
        "60187" to 40.0,
        "60188" to 40.0,
        "60189" to 35.0,
        "60190" to 40.0
    )

    constructor(parcel: Parcel) : this() {
        selectedMulchType = parcel.readString().toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_mulch)

        selectedMulchType = intent.getStringExtra("selectedMulchType") ?: ""

        cubicYardsInput = findViewById(R.id.cubicYardsInput)
        streetInput = findViewById(R.id.streetInput)
        cityInput = findViewById(R.id.cityInput)
        stateInput = findViewById(R.id.stateInput)
        zipCodeInput = findViewById(R.id.zipCodeInput)
        emailInput = findViewById(R.id.emailInput)
        phoneInput = findViewById(R.id.phoneInput)
        nextButton = findViewById(R.id.nextButton)

        cubicYardsInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        zipCodeInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        nextButton.setOnClickListener {
            val cubicYards = cubicYardsInput.text.toString()
            val street = streetInput.text.toString()
            val city = cityInput.text.toString()
            val state = stateInput.text.toString()
            val zipCode = zipCodeInput.text.toString()
            val email = emailInput.text.toString()
            val phone = phoneInput.text.toString()

            if (cubicYards.isNotBlank() && street.isNotBlank() && city.isNotBlank() &&
                state.isNotBlank() && zipCode.isNotBlank() && email.isNotBlank() && phone.isNotBlank()) {

                val selectedMulchPrice = intent.getDoubleExtra("selectedMulchPrice", 0.0)
                val mulchCost = selectedMulchPrice * cubicYards.toDouble()

                val salesTax = mulchCost * SALES_TAX_RATE

                val deliveryCharge = DELIVERY_CHARGES[zipCode] ?: 0.0

                val totalCost = mulchCost + salesTax + deliveryCharge

                val mulchCostTextView = findViewById<TextView>(R.id.mulchCostTextView)
                mulchCostTextView.text = "Mulch Cost: $$mulchCost"

                val salesTaxTextView = findViewById<TextView>(R.id.salesTaxTextView)
                salesTaxTextView.text = "Sales Tax: $$salesTax"

                val deliveryChargeTextView = findViewById<TextView>(R.id.deliveryChargeTextView)
                deliveryChargeTextView.text = "Delivery Charge: $$deliveryCharge"

                val totalCostTextView = findViewById<TextView>(R.id.totalCostTextView)
                totalCostTextView.text = "Total Cost: $$totalCost"

                val mulchTypeAndCostTextView = findViewById<TextView>(R.id.mulchTypeAndCost)

                mulchTypeAndCostTextView.text = "Selected Mulch Type: $selectedMulchType ($$selectedMulchPrice per cubic yard)"

                val intent = Intent(this@OrderMulchActivity, OrderSummaryActivity::class.java)

                intent.putExtra("selectedMulchType", selectedMulchType)
                intent.putExtra("cubicYards", cubicYards)
                intent.putExtra("street", street)
                intent.putExtra("city", city)
                intent.putExtra("state", state)
                intent.putExtra("zipCode", zipCode)
                intent.putExtra("email", email)
                intent.putExtra("phone", phone)
                intent.putExtra("mulchCost", mulchCost)
                intent.putExtra("salesTax", salesTax)
                intent.putExtra("deliveryCharge", deliveryCharge)
                intent.putExtra("totalCost", totalCost)

                startActivity(intent)
            } else {

                val errorMessage = "Please fill in all required fields"

            }
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(selectedMulchType)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderMulchActivity> {
        override fun createFromParcel(parcel: Parcel): OrderMulchActivity {
            return OrderMulchActivity(parcel)
        }

        override fun newArray(size: Int): Array<OrderMulchActivity?> {
            return arrayOfNulls(size)
        }
    }
}
