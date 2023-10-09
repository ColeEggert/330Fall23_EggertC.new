package com.example.mulchapp


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast


class MainActivity() : AppCompatActivity(), Parcelable {


    private lateinit var mulchRadioGroup: RadioGroup
    private lateinit var nextButton: Button


    constructor(parcel: Parcel) : this() {


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        mulchRadioGroup = findViewById(R.id.mulchRadioGroup)
        nextButton = findViewById(R.id.nextButton)


        nextButton.setOnClickListener {
            val selectedRadioButton = findViewById<RadioButton>(mulchRadioGroup.checkedRadioButtonId)


            if (selectedRadioButton != null) {
                val selectedMulchType = selectedRadioButton.text.toString()
                val selectedMulchPrice = selectedRadioButton.tag.toString().toDouble()


                val intent = Intent(this@MainActivity, OrderMulchActivity::class.java)
                intent.putExtra("selectedMulchType", selectedMulchType)
                intent.putExtra("selectedMulchPrice", selectedMulchPrice)
                startActivity(intent)
            } else {
                Toast.makeText(this@MainActivity, "Please select a mulch type", Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {


    }


    override fun describeContents(): Int {
        return 0
    }


    companion object CREATOR : Parcelable.Creator<MainActivity> {
        override fun createFromParcel(parcel: Parcel): MainActivity {
            return MainActivity(parcel)
        }


        override fun newArray(size: Int): Array<MainActivity?> {
            return arrayOfNulls(size)
        }
    }
}
