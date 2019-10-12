package xyz.rtxux.game.shisanshui.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import xyz.rtxux.game.shisanshui.repository.CombatRepository
import xyz.rtxux.game.shisanshui.repository.UserCombatRepository
import xyz.rtxux.game.shisanshui.repository.UserRepository
import xyz.rtxux.game.shisanshui.util.CompareContext
import java.time.Instant
import javax.transaction.Transactional

@Service
class CalcService @Autowired constructor(
        private val userRepository: UserRepository,
        private val combatRepository: CombatRepository,
        private val userCombatRepository: UserCombatRepository
) {
    @Transactional
    @Scheduled(cron = "0/10 * *  * * ? ")
    fun calcAll() {
        val combatCalcList = combatRepository.findAllFinishedNotCalculatedRoom()
        for (combat in combatCalcList) {
            val userCombatList = combat.users!!
            val compareContext = CompareContext(userCombatList)
            val result = compareContext.run()
            result.forEach {
                val userCombat = it.key
                val user = userCombat.user!!
                userCombat.deltaScore = it.value
                userCombat.originalScore = user.score
                val newScore = user.score!! + it.value
                user.score = newScore
            }
            combat.finishTime = Instant.now()
            combatRepository.save(combat)
        }
    }
}