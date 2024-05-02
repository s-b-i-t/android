package com.example.homework3

import androidx.lifecycle.ViewModel
import kotlin.random.Random

class RobotViewModel : ViewModel() {
    private var _turnCount = 0
    var turnCount: Int
        get() = _turnCount
        set(value) {
            _turnCount = value
        }

    val robotList = listOf(
        Robot(false, R.drawable.king_of_detroit_robot_red_large, R.drawable.king_of_detroit_robot_red_small, 0),
        Robot(false, R.drawable.king_of_detroit_robot_white_large, R.drawable.king_of_detroit_robot_white_small, 0),
        Robot(false, R.drawable.king_of_detroit_robot_yellow_large, R.drawable.king_of_detroit_robot_yellow_small, 0)
    )

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

    fun updateRobotText(): String {
        return when (turnCount) {
            1 -> "Red Robot's Turn"
            2 -> "White Robot's Turn"
            3 -> "Yellow Robot's Turn"
            else -> ""
        }
    }

    // Utilize Reward's availableRewards for getting random rewards
    fun getRandomRewards(): List<Reward> {
        val availableRewards = Reward.availableRewards
        if (availableRewards.size <= 3) {
            return availableRewards.toList()
        }
        val selectedRewards = mutableSetOf<Reward>()

        while (selectedRewards.size < 3) {
            val randomIndex = Random.nextInt(availableRewards.size)
            selectedRewards.add(availableRewards[randomIndex])
        }
        // compare with name if cost is the same (ensures e is before f and c is before d)
        return selectedRewards.sortedWith(compareBy({ it.cost }, { it.name }))
    }

    fun removeReward(reward: Reward) {
        Reward.availableRewards.remove(reward)
    }

}
