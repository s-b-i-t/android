package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView


class MainActivity : AppCompatActivity() {

    private lateinit var imageViewYellowLarge: ImageButton
    private lateinit var imageViewRedLarge: ImageButton
    private lateinit var imageViewWhiteLarge: ImageButton
    private lateinit var imageViewYellowSmall: ImageButton
    private lateinit var imageViewRedSmall: ImageButton
    private lateinit var imageViewWhiteSmall: ImageButton
    private lateinit var imageButtonClockwise: ImageButton
    private lateinit var imageButtonCounterClockwise: ImageButton

    // first time clicked red should be the one to stay large
    private var currentLarge = "yellow"
    private var firstRun = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Images
        imageViewYellowLarge = findViewById(R.id.imageViewYellowLarge)
        imageViewRedLarge = findViewById(R.id.imageViewRedLarge)
        imageViewWhiteLarge = findViewById(R.id.imageViewWhiteLarge)
        imageViewYellowSmall = findViewById(R.id.imageViewYellowSmall)
        imageViewRedSmall = findViewById(R.id.imageViewRedSmall)
        imageViewWhiteSmall = findViewById(R.id.imageViewWhiteSmall)
        imageButtonClockwise = findViewById(R.id.imageButtonClockwise)
        imageButtonCounterClockwise = findViewById(R.id.imageButtonCounterClockwise)



        val images = listOf(imageViewYellowLarge, imageViewYellowSmall,
            imageViewRedLarge, imageViewRedSmall,
            imageViewWhiteLarge, imageViewWhiteSmall)


//         listen to any button being pressed by user and call taskOne
        for (image in images) {
            image.setOnClickListener { taskOne() }
        }

        imageButtonClockwise.setOnClickListener { taskOne() }
        imageButtonCounterClockwise.setOnClickListener { taskFour() }


    }

    private fun taskOne() {
        when (currentLarge) {
            "red" -> {
                // if red is currently large & button clicked white should be large now
                imageViewRedLarge.visibility = View.GONE
                imageViewRedSmall.visibility = View.VISIBLE

                imageViewWhiteLarge.visibility = View.VISIBLE
                imageViewWhiteSmall.visibility = View.GONE

                imageViewYellowLarge.visibility = View.GONE
                imageViewYellowSmall.visibility = View.VISIBLE


                currentLarge = "white"
            }
            "white" -> {
                // if white is currently large & button clicked yellow should be large now

                imageViewWhiteLarge.visibility = View.GONE
                imageViewWhiteSmall.visibility = View.VISIBLE

                imageViewYellowLarge.visibility = View.VISIBLE
                imageViewYellowSmall.visibility = View.GONE

                imageViewRedLarge.visibility = View.GONE
                imageViewRedSmall.visibility = View.VISIBLE
                currentLarge = "yellow"
            }
            "yellow" -> {

                // if yellow is currently large we go back to red being large now
                imageViewYellowLarge.visibility = View.GONE
                imageViewYellowSmall.visibility = View.VISIBLE

                imageViewRedLarge.visibility = View.VISIBLE
                imageViewRedSmall.visibility = View.GONE

                imageViewWhiteSmall.visibility = View.VISIBLE
                imageViewWhiteLarge.visibility = View.GONE


                currentLarge = "red"
            }
        }
    }

    // same as task 1 but goes red->yellow->white  instead of  red->white->yellow
    private fun taskFour() {


        when (currentLarge) {
            "red" -> {
                // if red is currently large & button clicked yellow should be large now
                imageViewRedLarge.visibility = View.GONE
                imageViewRedSmall.visibility = View.VISIBLE

                imageViewWhiteLarge.visibility = View.GONE
                imageViewWhiteSmall.visibility = View.VISIBLE

                imageViewYellowLarge.visibility = View.VISIBLE
                imageViewYellowSmall.visibility = View.GONE


                currentLarge = "yellow"
            }
            "white" -> {
                // if white is currently large & button clicked red should be large now

                imageViewWhiteLarge.visibility = View.GONE
                imageViewWhiteSmall.visibility = View.VISIBLE

                imageViewYellowLarge.visibility = View.GONE
                imageViewYellowSmall.visibility = View.VISIBLE

                imageViewRedLarge.visibility = View.VISIBLE
                imageViewRedSmall.visibility = View.GONE
                currentLarge = "red"
            }
            "yellow" -> {

                // if yellow is currently large we go back to white being large now


//        Order is slightly changed but we still want red to be the first to turn
                if (firstRun){
                    imageViewRedLarge.visibility = View.VISIBLE
                    imageViewRedSmall.visibility = View.GONE

                    imageViewWhiteSmall.visibility = View.VISIBLE
                    imageViewWhiteLarge.visibility = View.GONE

                    imageViewYellowLarge.visibility = View.GONE
                    imageViewYellowSmall.visibility = View.VISIBLE

                    currentLarge = "red"
                    firstRun = false

                }
                else {
                    imageViewYellowLarge.visibility = View.GONE
                    imageViewYellowSmall.visibility = View.VISIBLE

                    imageViewRedLarge.visibility = View.GONE
                    imageViewRedSmall.visibility = View.VISIBLE

                    imageViewWhiteSmall.visibility = View.GONE
                    imageViewWhiteLarge.visibility = View.VISIBLE


                    currentLarge = "white"
                }
            }
        }
    }
}













