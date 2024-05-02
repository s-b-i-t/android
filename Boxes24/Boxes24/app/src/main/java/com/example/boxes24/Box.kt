package com.example.boxes24

import android.graphics.PointF

data class Box (val start: PointF){
    var end: PointF = start

    val left: Float
        get() = Math.min(start.x, end.x)

    val right: Float
        get() = Math.max(start.x, end.x)

    val top: Float
        get() = Math.min(start.y, end.y)

    val bottom: Float
        get() = Math.max(start.y, end.y)

    //HW6a
    val width: Float
        get() = Math.abs(end.x - start.x)

    val height: Float
        get() = Math.abs(end.y - start.y)
}