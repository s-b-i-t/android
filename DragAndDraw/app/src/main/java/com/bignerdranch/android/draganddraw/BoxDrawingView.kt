package com.bignerdranch.android.draganddraw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

private const val TAG = "BoxDrawingView"

class BoxDrawingView (
    context: Context,
    attrs: AttributeSet? = null
): View(context, attrs) {

    private var currentBox: Box? = null
    private val boxes = mutableListOf<Box>()
    private val boxPaint = Paint().apply {
        color=0x22ff0000.toInt()
    }
    private val backgroundPaint = Paint().apply {
        color= 0xfff8efe0.toInt()
    }

    var numBoxes = 0
    override fun onTouchEvent(event: MotionEvent): Boolean {

        if (numBoxes >= 3) {
            return true
        }

        var current = PointF(event.x, event.y)
        var action = ""
        when(event.action){
            MotionEvent.ACTION_DOWN ->{
                action="ACTION_DOWN"
                currentBox = Box(current).also{
                    boxes.add(it)
                }
            }
            MotionEvent.ACTION_MOVE ->{
                action="ACTION_MOVE"
                updateCurrentBox(current)
            }
            MotionEvent.ACTION_UP ->{
                action = "ACTION_UP"
                updateCurrentBox(current)
                currentBox = null
                numBoxes++
            }
            MotionEvent.ACTION_CANCEL ->{
                action = "ACTION_CANCEL"
                currentBox = null
            }
        }
        return true
    }

    private fun updateCurrentBox(current: PointF){
        currentBox?.let{
            it.end = current
            invalidate()
        }
    }


//    override fun onDraw(canvas: Canvas) {
//        canvas.drawPaint(backgroundPaint)
//        boxes.forEach { box ->
//            val side = Math.min(box.width, box.height)
//            canvas.drawRect(box.left, box.top, box.left + side, box.top + side, boxPaint)
//        }
//    }


    override fun onDraw(canvas: Canvas) {
        canvas.drawPaint(backgroundPaint)
        boxes.forEach { box ->
            if (box.width > box.height) {
                val side = box.height
                canvas.drawRect(box.left, box.top, box.left + side, box.top + side, boxPaint)
            } else {
                canvas.drawOval(box.left, box.top, box.right, box.bottom, boxPaint)
            }
        }
    }


}
