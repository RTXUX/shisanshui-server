package xyz.rtxux.game.shisanshui.util

import xyz.rtxux.game.shisanshui.logic.Card
import xyz.rtxux.game.shisanshui.logic.HelpUtil

object GameUtil {

    fun checkCheat(cards: List<Card>, originalCards: List<Card>): Boolean {
        if (cards.size != originalCards.size) return false
        if (originalCards.containsAll(cards) && cards.containsAll(cards)) return true
        return false
    }

    fun checkSanity(cards: List<List<Card>>): Boolean {
        return true
    }

    fun stringToCards(string: String): List<Card> {
        return string.split(' ').map {
            HelpUtil.convertStringToSingleCard(it)
        }.toList()
    }

    fun randomCards(): List<Card> {
        return HelpUtil.cache.values.shuffled()
    }

}