package com.example.homework3

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

private const val TAG = "RobotViewModel"
class RobotViewModel : ViewModel(){
    private var _turnCount = 0
    var turnCount : Int
        get() = _turnCount
        set(value) {_turnCount = value}

    val robotList = listOf(
        Robot(false, R.drawable.king_of_detroit_robot_red_large, R.drawable.king_of_detroit_robot_red_small, 0),
        Robot(false, R.drawable.king_of_detroit_robot_white_large, R.drawable.king_of_detroit_robot_white_small, 0),
        Robot(false, R.drawable.king_of_detroit_robot_yellow_large, R.drawable.king_of_detroit_robot_yellow_small, 0)
    )

    private val allRewards = listOf(
        Reward("A", 1),
        Reward("B", 2),
        Reward("C", 3),
        Reward("D", 3),
        Reward("E", 4),
        Reward("F", 4),
        Reward("G", 7)
    )

    private var availableRewards = allRewards.toMutableList()



    val currentRobot: Robot
        get() = robotList[turnCount - 1]

    fun advanceTurn() {
        _turnCount += 1
        if (_turnCount > robotList.size) {
            _turnCount = 1
        }
        currentRobot.myEnergy += 1
        robotList.forEachIndexed { index, robot ->
            robot.myTurn = index == _turnCount - 1
        }
    }

    fun updateRobotText() : String {
        val currentTurnText = when (turnCount) {
            1 -> "Red Robot's Turn"
            2 -> "White Robot's Turn"
            3 -> "Yellow Robot's Turn"
            else -> "Turn information is unavailable"
        }
        return currentTurnText
    }

    // Function to get three random, available rewards
    fun getRandomRewards(): List<Reward> {
        if (availableRewards.size <= 3) {
            return availableRewards.toList() // Return all if 3 or fewer
        }

        val selectedRewards = mutableSetOf<Reward>()
        while (selectedRewards.size < 3) {
            val randomIndex = Random.nextInt(availableRewards.size)
            selectedRewards.add(availableRewards[randomIndex])
        }
        return selectedRewards.sortedBy { it.cost } // Sort by cost to maintain order
    }

    // Call this method when a reward is purchased to remove it from the available list
    fun purchaseReward(reward: Reward) {
        availableRewards.remove(reward)
    }
}

