package com.example.homework3

data class Reward(val name: String, val cost: Int) {
    companion object {
        val allRewards = listOf(
            Reward("A", 1),
            Reward("B", 2),
            Reward("C", 3),
            Reward("D", 3),
            Reward("E", 4),
            Reward("F", 4),
            Reward("G", 7)
        )

        val availableRewards = allRewards.toMutableList()
    }
}
