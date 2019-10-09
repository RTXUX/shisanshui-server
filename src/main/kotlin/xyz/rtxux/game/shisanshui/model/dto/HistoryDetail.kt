package xyz.rtxux.game.shisanshui.model.dto

data class HistoryDetail(
        val id: Int,
        val timestamp: Long,
        val detail: Iterable<HistoryPlayerDetail>
)

data class HistoryPlayerDetail(
        val playerId: Int,
        val name: String,
        val score: Int,
        val card: Iterable<String>
)