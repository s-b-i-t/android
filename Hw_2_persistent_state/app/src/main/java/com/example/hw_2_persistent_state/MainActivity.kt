package com.example.hw_2_persistent_state

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import android.widget.Toast
import com.example.hw_2_persistent_state.R

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var redImage : ImageView

    private lateinit var yellowImage : ImageView
    private lateinit var whiteImage : ImageView


    private lateinit var robotImages : MutableList<ImageView>

    // list of robot objects
    private val robotList = listOf(
        Robot(false, R.drawable.king_of_detroit_robot_red_large, R.drawable.king_of_detroit_robot_red_small),
        Robot(false, R.drawable.king_of_detroit_robot_white_large, R.drawable.king_of_detroit_robot_white_small),
        Robot(false, R.drawable.king_of_detroit_robot_yellow_large, R.drawable.king_of_detroit_robot_yellow_small)
    )

    private val robotViewModel : RobotViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        redImage = findViewById(R.id.red_robot)

        yellowImage = findViewById(R.id.yellow_robot)

        whiteImage = findViewById(R.id.white_robot)


        robotImages = mutableListOf(redImage, whiteImage, yellowImage)

        // toast for yellow robot

        redImage.setOnClickListener {
            advanceTurn()
        }

        yellowImage.setOnClickListener{
            advanceTurn()
            Toast.makeText(this, "turn count: ${robotViewModel.turnCount}", Toast.LENGTH_SHORT).show()
        }

        whiteImage.setOnClickListener {
            advanceTurn()
        }
        // initial update
        updateUI()
    }

    private fun advanceTurn(){
        robotViewModel.advanceTurn()
        updateUI()
    }

    private fun updateUI(){
        setTurn()
        setImages()
    }

    private fun setTurn() {
        // set all robot turns to false
        robotList.forEach { it.myTurn = false }

        // set corresponding robot objects myTurn to true
        if (robotViewModel.turnCount >= 1 && robotViewModel.turnCount <= robotList.size) {
            robotList[robotViewModel.turnCount - 1].myTurn = true
        }
    }

    private fun setImages() {
        // if turn count is 0 all robots should be large
        if (robotViewModel.turnCount == 0) {
            for (i in robotImages.indices) {
                val imageView = robotImages[i]
                imageView.setImageResource(robotList[i].largeImgRes)
            }
        }

        else {
            // turn corresponding image to large (i = 1 -> red, i = 2 -> white, i = 3 ->yellow)
            for (i in robotList.indices) {

                val imageRes: Int = if (robotList[i].myTurn)
                {
                    robotList[i].largeImgRes
                }

                else {
                    robotList[i].smallImgRes
                }

                robotImages[i].setImageResource(imageRes)
            }

        }
    }


}