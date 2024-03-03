package com.example.homework3

import androidx.lifecycle.ViewModel

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
}