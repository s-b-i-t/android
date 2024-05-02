package com.example.homework3

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels


const val EXTRA_ROBOT_ENERGY = "com.example.homework3.current_robot_energy"
const val EXTRA_ROBOT_PURCHASE_MADE = "com.example.homework3.current_robot_purchase_made"
const val EXTRA_ROBOT_IMAGE = "com.example.homework3.extra_robot_image"
private const val TAG = "RobotPurchase"
class RobotPurchase : AppCompatActivity() {

    private lateinit var button1 : Button
    private lateinit var button2 : Button
    private lateinit var button3 : Button
    private lateinit var robot_energy_available : TextView
    private lateinit var back_button : ImageView
    private lateinit var reward_1_cost : TextView
    private lateinit var reward_2_cost : TextView
    private lateinit var reward_3_cost : TextView
    private lateinit var energy_counter_1 : ImageView
    private lateinit var energy_counter_2 : ImageView
    private lateinit var energy_counter_3 : ImageView


    private var robot_energy = 0


    private val robotViewModel : RobotViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_robot_purchase)

        button1 = findViewById(R.id.button_1)
        button2 = findViewById(R.id.button_2)
        button3 = findViewById(R.id.button_3)


        robot_energy_available = findViewById(R.id.robot_energy_available)
        back_button = findViewById(R.id.back_button)


        //get the proper robot image (task 3)
        val robotImageResId = intent.getIntExtra(EXTRA_ROBOT_IMAGE, 0)



        robot_energy = intent.getIntExtra(EXTRA_ROBOT_ENERGY, 6)
        robot_energy_available.text = robot_energy.toString()
        val robotImageView: ImageView = findViewById(R.id.white_robot)
        robotImageView.setImageResource(robotImageResId)


        reward_1_cost = findViewById(R.id.reward_1_cost)
        reward_2_cost = findViewById(R.id.reward_2_cost)
        reward_3_cost = findViewById(R.id.reward_3_cost)

        energy_counter_1 = findViewById(R.id.energy_counter_1)
        energy_counter_2 = findViewById(R.id.energy_counter_2)
        energy_counter_3 = findViewById(R.id.energy_counter_3)


        updateRewardsUI()

        // go back to main activity
        back_button.setOnClickListener {
            finish()
        }

        val currentRewards = robotViewModel.getRandomRewards()

        button1.setOnClickListener{_: View ->
            makePurchase(currentRewards[0])
        }
        button2.setOnClickListener{_: View ->
            makePurchase(currentRewards[1])
        }
        button3.setOnClickListener{_: View ->
            makePurchase(currentRewards[2])
        }
    }



    companion object {
        fun newIntent(packageContext: Context, robot_energy: Int, robotImageResId: Int): Intent {
            return Intent(packageContext, RobotPurchase::class.java).apply {
                putExtra(EXTRA_ROBOT_ENERGY, robot_energy)
                putExtra(EXTRA_ROBOT_IMAGE, robotImageResId)
            }
        }
    }

    private fun makePurchase(reward: Reward) {
        val costOfPurchase = reward.cost
        if (robot_energy >= costOfPurchase) {
            robot_energy -= costOfPurchase

            robotViewModel.removeReward(reward)

            robot_energy_available.text = robot_energy.toString()

            // Update  list of available rewards
            updateRewardsUI()

            val data = Intent().apply {
                putExtra(EXTRA_ROBOT_PURCHASE_MADE, reward.name)
                putExtra(EXTRA_ROBOT_ENERGY, robot_energy)
            }
            setResult(Activity.RESULT_OK, data)
             finish()
        } else {
            Toast.makeText(this, "Not enough energy to purchase", Toast.LENGTH_SHORT).show()
        }
    }


    private fun updateRewardsUI() {
        val currentRewards = robotViewModel.getRandomRewards()

        // Update UI based on number of available rewards
        when (currentRewards.size) {
            0 -> {
                button1.visibility = View.GONE
                button2.visibility = View.GONE
                button3.visibility = View.GONE

                reward_1_cost.visibility = View.GONE
                reward_2_cost.visibility = View.GONE
                reward_3_cost.visibility = View.GONE

                energy_counter_1.visibility = View.GONE
                energy_counter_2.visibility = View.GONE
                energy_counter_3.visibility = View.GONE



            }
            1 -> {
                button1.visibility = View.VISIBLE
                button1.text = "Reward\n${currentRewards[0].name}"
                reward_1_cost.text = "${currentRewards[0].cost}"
                button1.setOnClickListener { makePurchase(currentRewards[0]) }

                button2.visibility = View.GONE
                button3.visibility = View.GONE

                reward_2_cost.visibility = View.GONE
                reward_3_cost.visibility = View.GONE

                energy_counter_2.visibility = View.GONE
                energy_counter_3.visibility = View.GONE

            }
            2 -> {
                button1.visibility = View.VISIBLE
                button1.text = "Reward\n${currentRewards[0].name}"
                reward_1_cost.text = "${currentRewards[0].cost}"
                button1.setOnClickListener { makePurchase(currentRewards[0]) }

                button2.visibility = View.VISIBLE
                button2.text = "Reward\n${currentRewards[1].name}"
                reward_2_cost.text = "${currentRewards[1].cost}"
                button2.setOnClickListener { makePurchase(currentRewards[1]) }

                button3.visibility = View.GONE
                reward_3_cost.visibility = View.GONE
                energy_counter_3.visibility = View.GONE


            }
            else -> {
                // All 3 rewards avaliable
                button1.visibility = View.VISIBLE
                button1.text = "Reward\n${currentRewards[0].name}"
                reward_1_cost.text = "${currentRewards[0].cost}"
                button1.setOnClickListener { makePurchase(currentRewards[0]) }

                button2.visibility = View.VISIBLE
                button2.text = "Reward\n${currentRewards[1].name}"
                reward_2_cost.text = "${currentRewards[1].cost}"
                button2.setOnClickListener { makePurchase(currentRewards[1]) }

                button3.visibility = View.VISIBLE
                button3.text = "Reward\n${currentRewards[2].name}"
                reward_3_cost.text = "${currentRewards[2].cost}"
                button3.setOnClickListener { makePurchase(currentRewards[2]) }
            }
        }
    }

}

