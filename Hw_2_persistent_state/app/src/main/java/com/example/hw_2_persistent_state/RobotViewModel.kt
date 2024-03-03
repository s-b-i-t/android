package com.example.hw_2_persistent_state

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "RobotViewModel"
class RobotViewModel : ViewModel(){
    private var _turnCount = 0

    var turnCount : Int
        get() = _turnCount
        set(value) {_turnCount = value}


    fun advanceTurn() {
        _turnCount += 1
        if (_turnCount > 3) {
            _turnCount = 1
        }
    }
}