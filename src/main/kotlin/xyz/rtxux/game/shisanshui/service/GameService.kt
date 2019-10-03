package xyz.rtxux.game.shisanshui.service

import xyz.rtxux.game.shisanshui.logic.Card

interface GameService {
    fun submitCards(playerId: Int, combatId: Int, cards: List<List<Card>>)
}