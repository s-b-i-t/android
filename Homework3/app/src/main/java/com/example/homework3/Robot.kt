package com.example.homework3

data class Robot(
    var myTurn: Boolean,
    val largeImgRes : Int,
    val smallImgRes : Int,
    var myEnergy : Int,
    var lastItemPurchased: String? = null,
    var allItemsPurchased: MutableList<String> = mutableListOf()
)