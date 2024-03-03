package com.example.homework3

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

const val EXTRA_ROBOT_ENERGY = "com.example.homework3.current_robot_energy"
const val EXTRA_ROBOT_PURCHASE_MADE = "com.example.homework3.current_robot_purchase_made"

class RobotPurchase : AppCompatActivity() {

    private lateinit var buttona : Button
    private lateinit var buttonb : Button
    private lateinit var buttonc : Button
    private lateinit var robot_energy_available : TextView
    private lateinit var back_button : ImageView
    private var robot_energy = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_robot_purchase)

        buttona = findViewById(R.id.button_a)
        buttonb = findViewById(R.id.button_b)
        buttonc = findViewById(R.id.button_c)
        robot_energy_available = findViewById(R.id.robot_energy_available)
        back_button = findViewById(R.id.back_button)


        robot_energy = intent.getIntExtra(EXTRA_ROBOT_ENERGY, 6)
        robot_energy_available.setText(robot_energy.toString())


        // go back to main activity
        back_button.setOnClickListener {
            finish()
        }


        buttona.setOnClickListener{_: View ->
            makePurchase("A", 1)
        }
        buttonb.setOnClickListener{_: View ->
            makePurchase("B", 2)
        }
        buttonc.setOnClickListener{_: View ->
            makePurchase("C", 3)
        }
    }

    companion object{
        fun newIntent(packageContext: Context, robot_energy :Int): Intent {
            return Intent(packageContext, RobotPurchase::class.java).apply{
                putExtra(EXTRA_ROBOT_ENERGY, robot_energy)
            }
        }
    }

    private fun makePurchase(buttonType: String, costOfPurchase: Int){
        if (robot_energy >= costOfPurchase) {
            robot_energy -= costOfPurchase
            val data = Intent().apply {
                putExtra(EXTRA_ROBOT_PURCHASE_MADE, buttonType)
                putExtra(EXTRA_ROBOT_ENERGY, robot_energy)
            }
            setResult(Activity.RESULT_OK, data)
            finish()
        } else {
            Toast.makeText(this, "Not enough energy to purchase", Toast.LENGTH_SHORT).show()
        }
    }
}
