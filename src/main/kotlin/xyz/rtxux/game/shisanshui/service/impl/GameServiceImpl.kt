package xyz.rtxux.game.shisanshui.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import xyz.rtxux.game.shisanshui.compare.CardSet
import xyz.rtxux.game.shisanshui.compare.CompareCtrl
import xyz.rtxux.game.shisanshui.exception.AppException
import xyz.rtxux.game.shisanshui.logic.Card
import xyz.rtxux.game.shisanshui.model.domain.UserCombatId
import xyz.rtxux.game.shisanshui.repository.CombatRepository
import xyz.rtxux.game.shisanshui.repository.UserCombatRepository
import xyz.rtxux.game.shisanshui.repository.UserRepository
import xyz.rtxux.game.shisanshui.service.GameService
import xyz.rtxux.game.shisanshui.util.GameUtil

@Service
class GameServiceImpl @Autowired constructor(
        private val userRepository: UserRepository,
        private val combatRepository: CombatRepository,
        private val userCombatRepository: UserCombatRepository,
        private val compareCtrl: CompareCtrl
) : GameService {
    override fun submitCards(playerId: Int, combatId: Int, cards: List<List<Card>>) {
        val userCombat = userCombatRepository.findById(UserCombatId(playerId, combatId)).orElseThrow { AppException("Corresponding combat not found", null, 2003) }
        if (userCombat.card!!.size != 1) {
            throw AppException("Illegal card status", null, 2003)
        }
        val originalCardString = userCombat.card!![0]
        val originalCards = GameUtil.stringToCards(originalCardString)
        if (!GameUtil.checkCheat(cards.flatMap { it }.toList(), originalCards)) {
            throw AppException("Cheat!!!", null, 2002)
        }
        val flatCard = cards.flatMap { it }.toMutableList()
        CardSet.sortCards(flatCard)
        val specialWeight = compareCtrl.calcSpecial(flatCard)
        if (specialWeight > 0) {
            userCombat.special = specialWeight
            userCombat.card = arrayOf(flatCard.joinToString(" "))
            userCombatRepository.save(userCombat)
            return
        }
        val weights = IntArray(3) { 0 }
        val mutableCards = cards.map { it.toMutableList() }
        mutableCards.forEachIndexed { index, mutableList ->
            CardSet.sortCards(mutableList)
            weights[index] = compareCtrl.calcWithoutSort(mutableList)
        }
        for (i in 0..2) {
            if (!(weights[i] < weights[i + 1])) {
                throw AppException("Illegal combinations", null, 2003)
            }
        }
        userCombat.card = cards.map {
            it.joinToString(" ")
        }.toTypedArray()
        userCombat.weight = weights.toTypedArray()
        userCombatRepository.save(userCombat)
//        if (!GameUtil.checkSanity(cards)) {
//            throw AppException("Illegal combinations", null, 2003)
//        }
//        userCombat.card = cards.map {
//            it.joinToString(" ")
//        }.toTypedArray()
        userCombatRepository.save(userCombat)
    }


}