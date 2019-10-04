package xyz.rtxux.game.shisanshui.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import xyz.rtxux.game.shisanshui.model.dto.OpenCombatDTO
import xyz.rtxux.game.shisanshui.model.dto.SubmitDTO
import xyz.rtxux.game.shisanshui.repository.CombatRepository
import xyz.rtxux.game.shisanshui.repository.UserCombatRepository
import xyz.rtxux.game.shisanshui.service.GameService
import xyz.rtxux.game.shisanshui.util.GameUtil

@RestController
@RequestMapping("/game")
class GameController @Autowired constructor(
        private val gameService: GameService,
        private val combatRepository: CombatRepository,
        private val userCombatRepository: UserCombatRepository
) {

    @RequestMapping("/submit", method = arrayOf(RequestMethod.POST))
    fun submit(@RequestBody submitDTO: SubmitDTO, @AuthenticationPrincipal userId: Int): Map<String, Any> {
        val cardGroup = submitDTO.card!!.map {
            GameUtil.stringToCards(it)
        }.toList()
        gameService.submitCards(userId, submitDTO.id!!, cardGroup)
        return mapOf(
                Pair("msg", "Success")
        )
    }

    @RequestMapping("/open", method = arrayOf(RequestMethod.POST))
    fun open(@AuthenticationPrincipal userId: Int): OpenCombatDTO {
        val availableCombat = combatRepository.findAllAvailableRoomForPlayer(userId)
        if (availableCombat.size > 0) {
            for (combat in availableCombat) {
                try {
                    return gameService.joinCombat(userId, combat.id!!)
                } catch (e: Exception) {
                    continue
                }
            }
        }
        return gameService.newCombat(userId)
    }


}