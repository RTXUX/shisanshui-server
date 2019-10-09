package xyz.rtxux.game.shisanshui.model.dto

data class HistoryListEntry(
        val id: Int,
        val card: Iterable<String>,
        val score: Int,
        val timestamp: Int
)