package com.example.homework3


import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts


private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private lateinit var yellow_img : ImageView
    private lateinit var red_img : ImageView
    private lateinit var white_img : ImageView
    private lateinit var purchase_button : Button
    private lateinit var tvCurrentTurn: TextView

    private lateinit var robotImages : MutableList<ImageView>

    private val robotViewModel : RobotViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        yellow_img = findViewById(R.id.yellow_robot)
        red_img = findViewById(R.id.red_robot)
        white_img = findViewById(R.id.white_robot)
        purchase_button = findViewById(R.id.purchase_button)
        tvCurrentTurn = findViewById(R.id.tv_current_turn)


        robotImages = mutableListOf(red_img, white_img, yellow_img)

        yellow_img.setOnClickListener{_: View ->
            advanceTurn()
            tvCurrentTurn.text = robotViewModel.updateRobotText()

            Toast.makeText(this, "Turn Count: ${robotViewModel.turnCount}", Toast.LENGTH_SHORT).show()
        }
        red_img.setOnClickListener {
            advanceTurn()
            tvCurrentTurn.text = robotViewModel.updateRobotText()

        }
        white_img.setOnClickListener {
            advanceTurn()
            tvCurrentTurn.text = robotViewModel.updateRobotText()

            Toast.makeText(this, "MyEnergy: " +
                    "${robotViewModel.robotList[robotViewModel.turnCount-1].myEnergy}", Toast.LENGTH_SHORT).show()
        }
        purchase_button.setOnClickListener {
            if (robotViewModel.turnCount != 0) {
                val currentRobotIndex = robotViewModel.turnCount - 1
                val currentRobot = robotViewModel.robotList[currentRobotIndex]
                val intent = RobotPurchase.newIntent(
                    this,
                    currentRobot.myEnergy,
                    currentRobot.largeImgRes
                )
                purchaseLauncher.launch(intent)
            }
        }
        updateUI()
    }

    private val purchaseLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result ->

        if(result.resultCode == Activity.RESULT_CANCELED){
            Toast.makeText(this,"Data Canceled? ", Toast.LENGTH_SHORT).show()
        }
        if(result.resultCode == Activity.RESULT_OK) {
            val robotPurchaseMade = result.data?.getStringExtra(EXTRA_ROBOT_PURCHASE_MADE)
            val newEnergyLevel = result.data?.getIntExtra(EXTRA_ROBOT_ENERGY, -1)

            if (robotPurchaseMade != null && newEnergyLevel != null && newEnergyLevel >= 0) {
                val currentRobot = robotViewModel.currentRobot
                currentRobot.lastItemPurchased = robotPurchaseMade
                currentRobot.allItemsPurchased.add(robotPurchaseMade)
                currentRobot.myEnergy = newEnergyLevel
            }
        }
    }

    private fun updateUI(){
        setTurn()
        setImages()
    }

    private fun advanceTurn(){
        robotViewModel.advanceTurn()

        //get last item purchased
        if (robotViewModel.currentRobot.lastItemPurchased != null) {
            Toast.makeText(
                this,
                "Items Purchased: ${getAllItemsPurchased()}",
                Toast.LENGTH_LONG
            ).show()
        }
        setImages()
    }

    private fun setTurn() {
        // set all robot turns to false
        robotViewModel.robotList.forEach { it.myTurn = false }

        // set corresponding robot objects myTurn to true
        if (robotViewModel.turnCount >= 1 && robotViewModel.turnCount <= robotViewModel.robotList.size) {
            robotViewModel.robotList[robotViewModel.turnCount - 1].myTurn = true
        }
    }

    private fun setImages() {
        val robots = robotViewModel.robotList

        // if turn count is 0 all robots should be large
        if (robotViewModel.turnCount == 0) {
            robotImages.forEachIndexed { index, imageView ->
                imageView.setImageResource(robots[index].largeImgRes)
            }
        } else {
            for (i in robotViewModel.robotList.indices) {
                val imageRes: Int = if (robotViewModel.robotList[i].myTurn)
                {
                    robotViewModel.robotList[i].largeImgRes
                }
                else {
                    robotViewModel.robotList[i].smallImgRes
                }
                robotImages[i].setImageResource(imageRes)
            }
        }
    }

    private fun getAllItemsPurchased(): String {
        val purchases = robotViewModel.currentRobot.allItemsPurchased

        return purchases.joinToString(separator = ", ")
    }






}
