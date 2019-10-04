package xyz.rtxux.game.shisanshui.service

import xyz.rtxux.game.shisanshui.logic.Card
import xyz.rtxux.game.shisanshui.model.dto.OpenCombatDTO

interface GameService {
    fun submitCards(playerId: Int, combatId: Int, cards: List<List<Card>>)
    fun joinCombat(playerId: Int, combatId: Int): OpenCombatDTO
    fun newCombat(playerId: Int): OpenCombatDTO
}