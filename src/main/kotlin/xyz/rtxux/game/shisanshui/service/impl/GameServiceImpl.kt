package xyz.rtxux.game.shisanshui.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import xyz.rtxux.game.shisanshui.compare.CardSet
import xyz.rtxux.game.shisanshui.compare.CompareCtrl
import xyz.rtxux.game.shisanshui.exception.AppException
import xyz.rtxux.game.shisanshui.logic.Card
import xyz.rtxux.game.shisanshui.model.domain.CombatDO
import xyz.rtxux.game.shisanshui.model.domain.UserCombatDO
import xyz.rtxux.game.shisanshui.model.domain.UserCombatId
import xyz.rtxux.game.shisanshui.model.dto.OpenCombatDTO
import xyz.rtxux.game.shisanshui.repository.CombatRepository
import xyz.rtxux.game.shisanshui.repository.UserCombatRepository
import xyz.rtxux.game.shisanshui.repository.UserRepository
import xyz.rtxux.game.shisanshui.service.GameService
import xyz.rtxux.game.shisanshui.util.GameUtil
import java.time.Duration
import java.time.Instant

@Service
class GameServiceImpl @Autowired constructor(
        private val userRepository: UserRepository,
        private val combatRepository: CombatRepository,
        private val userCombatRepository: UserCombatRepository,
        private val compareCtrl: CompareCtrl
) : GameService {

    @Transactional
    override fun submitCards(playerId: Int, combatId: Int, cards: List<List<Card>>) {
        if (cards.size != 3) throw AppException("Format Error", null, 2005)
        val userCombat = userCombatRepository.findById(UserCombatId(playerId, combatId)).orElseThrow { AppException("Corresponding combat not found", null, 2003) }
        if (userCombat.timestamp != null) {
            throw AppException("Illegal card status", null, 2003)
        }
        if (Duration.between(userCombat.startTime!!, Instant.now()).seconds > 10) throw AppException("Timeout", null, 2006)
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
            userCombat.timestamp = Instant.now()
            userCombatRepository.save(userCombat)
            return
        }
        val weights = IntArray(3) { 0 }
        val mutableCards = cards.map { it.toMutableList() }
        mutableCards.forEachIndexed { index, mutableList ->
            CardSet.sortCards(mutableList)
            weights[index] = compareCtrl.calcWithoutSort(mutableList)
            if (weights[index] == -1) throw AppException("Illegal combinations", null, 2004)
        }
        for (i in 0..1) {
            if (!(weights[i] <= weights[i + 1])) {
                throw AppException("Illegal combinations", null, 2004)
            }
        }
        userCombat.card = cards.map {
            it.joinToString(" ")
        }.toTypedArray()
        userCombat.weight = weights.toTypedArray()
        userCombat.timestamp = Instant.now()
        userCombatRepository.save(userCombat)
//        if (!GameUtil.checkSanity(cards)) {
//            throw AppException("Illegal combinations", null, 2003)
//        }
//        userCombat.card = cards.map {
//            it.joinToString(" ")
//        }.toTypedArray()
        userCombatRepository.save(userCombat)
    }

    @Transactional
    override fun joinCombat(playerId: Int, combatId: Int): OpenCombatDTO {
        val combat = combatRepository.findByIdForUpdate(combatId).orElseThrow { AppException("Room not found", null, 5000) }
        val usersInCombat = combat.users!!
        if (usersInCombat.size == 0 || usersInCombat.size > 3 || usersInCombat.any { it.id!!.userId == playerId }) {
            throw IllegalArgumentException("Room full or player already in room")
        }
        val cardSequenceString = combat.cardSequence!!
        val cardSequence = GameUtil.stringToCards(cardSequenceString)
        val cardToSend = cardSequence.subList((usersInCombat.size) * 13, (usersInCombat.size + 1) * 13)
        if (cardToSend.size != 13) {
            throw AppException("something is wrong", null, 5000)
        }
        val openCombatDTO = OpenCombatDTO(id = combatId, card = cardToSend.joinToString(" "))
        val userCombatDO = UserCombatDO(
                id = UserCombatId(userId = playerId, combatId = combatId),
                card = arrayOf(openCombatDTO.card),
                startTime = Instant.now(),
                user = userRepository.findById(playerId).orElseThrow(),
                combat = combat
        )
        combat.users!!.add(userCombatDO)
        //combatRepository.save(combat)
        userCombatRepository.save(userCombatDO)
        return openCombatDTO
    }

    @Transactional
    override fun newCombat(playerId: Int): OpenCombatDTO {
        val cardSequence = GameUtil.randomCards()
        var combat = CombatDO(cardSequence = cardSequence.joinToString(" "))
        combat = combatRepository.save(combat)
        val cardToSend = cardSequence.subList(0, 13)
        val openCombatDTO = OpenCombatDTO(id = combat.id!!, card = cardToSend.joinToString(" "))
        val userDO = userRepository.findById(playerId).orElseThrow()
        val userCombatDO = UserCombatDO(
                id = UserCombatId(userId = playerId, combatId = combat.id!!),
                user = userDO,
                combat = combat,
                card = arrayOf(openCombatDTO.card),
                startTime = Instant.now()
        )
        userCombatRepository.save(userCombatDO)
        return openCombatDTO
    }

}