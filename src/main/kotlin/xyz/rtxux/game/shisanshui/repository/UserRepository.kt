package xyz.rtxux.game.shisanshui.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import xyz.rtxux.game.shisanshui.model.domain.UserDO
import java.util.*

@Repository
interface UserRepository : JpaRepository<UserDO, Int> {
    fun findUserDOByUsername(username: String): Optional<UserDO>
    fun findByStudentNumber(studentNumber: String): Optional<UserDO>
    @Query("select count(uc.id.combatId) from UserCombatDO uc join CombatDO co on uc.id.combatId=co.id where uc.id.userId=:id and co.finishTime is null")
    fun countUnfinishedCombat(@Param("id") id: Int): Int
}