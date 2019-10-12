package xyz.rtxux.game.shisanshui.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.*
import xyz.rtxux.game.shisanshui.exception.AppException
import xyz.rtxux.game.shisanshui.model.dto.HistoryDetail
import xyz.rtxux.game.shisanshui.model.dto.HistoryListEntry
import xyz.rtxux.game.shisanshui.model.dto.HistoryPlayerDetail
import xyz.rtxux.game.shisanshui.repository.CombatRepository
import xyz.rtxux.game.shisanshui.repository.UserCombatRepository
import xyz.rtxux.game.shisanshui.repository.UserRepository

@RestController
@RequestMapping("/history")
class HistoryController @Autowired constructor(
        private val userRepository: UserRepository,
        private val combatRepository: CombatRepository,
        private val userCombatRepository: UserCombatRepository
) {
    @RequestMapping("", method = arrayOf(RequestMethod.GET))
    fun list(@RequestParam("player_id") playerId: Int?, @RequestParam("limit") limit: Int?, @RequestParam("page") page: Int?): List<HistoryListEntry> {
        val historyList = userCombatRepository.findAllByIdUserId(playerId!!, PageRequest.of(page!!, limit!!))
        return historyList.map {
            HistoryListEntry(
                    id = it.id!!.combatId!!,
                    card = it.card!!.toList(),
                    score = when (it.deltaScore) {
                        null -> 0; else -> it.deltaScore!!
                    },
                    timestamp = it.timestamp!!.epochSecond.toInt()
            )
        }.toList()
    }

    @RequestMapping("/{id}", method = arrayOf(RequestMethod.GET))
    fun detail(@PathVariable("id") combatId: Int?): HistoryDetail {
        val combat = combatRepository.findById(combatId!!).orElseThrow { AppException("Combat not found or not finished yet", null, 3001) }
        if (combat.finishTime == null) throw AppException("Combat not found or not finished yet", null, 3001)
        val userCombatList = combat.users!!
        return HistoryDetail(
                id = combatId,
                timestamp = combat.finishTime!!.epochSecond,
                detail = userCombatList.map {
                    val user = it.user!!
                    HistoryPlayerDetail(
                            name = user.username!!,
                            player_id = user.id!!,
                            score = it.deltaScore!!,
                            card = it.card!!.toList()
                    )
                }
        )
    }
}