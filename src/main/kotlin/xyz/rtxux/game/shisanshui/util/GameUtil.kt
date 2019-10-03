package xyz.rtxux.game.shisanshui.util

object GameUtil {

    fun checkCheat(cards: List<Card>, originalCards: List<Card>): Boolean {
        return cards.equals(originalCards)
    }

    fun checkSanity(cards: List<List<Card>>): Boolean {
        return true
    }
}