package xyz.rtxux.game.shisanshui.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import xyz.rtxux.game.shisanshui.exception.AppException
import xyz.rtxux.game.shisanshui.model.domain.UserCombatId
import xyz.rtxux.game.shisanshui.repository.CombatRepository
import xyz.rtxux.game.shisanshui.repository.UserCombatRepository
import xyz.rtxux.game.shisanshui.repository.UserRepository
import xyz.rtxux.game.shisanshui.service.GameService
import xyz.rtxux.game.shisanshui.util.Card
import xyz.rtxux.game.shisanshui.util.GameUtil
import xyz.rtxux.game.shisanshui.util.cardsToStr
import xyz.rtxux.game.shisanshui.util.strToCards

@Service
class GameServiceImpl @Autowired constructor(
        private val userRepository: UserRepository,
        private val combatRepository: CombatRepository,
        private val userCombatRepository: UserCombatRepository
) : GameService {
    override fun submitCards(playerId: Int, combatId: Int, cards: List<List<Card>>) {
        val userCombat = userCombatRepository.findById(UserCombatId(playerId, combatId)).orElseThrow { AppException("Corresponding combat not found", null, 2003) }
        if (userCombat.card!!.size != 1) {
            throw AppException("Illegal card status", null, 2003)
        }
        val originalCardString = userCombat.card!![0]
        val originalCards = strToCards(originalCardString)
        if (!GameUtil.checkCheat(cards.flatMap { it }.toList(), originalCards)) {
            throw AppException("Cheat!!!", null, 2002)
        }
        if (!GameUtil.checkSanity(cards)) {
            throw AppException("Illegal combinations", null, 2003)
        }
        userCombat.card = cards.map {
            cardsToStr(it)
        }.toTypedArray()
        userCombatRepository.save(userCombat)
    }


}