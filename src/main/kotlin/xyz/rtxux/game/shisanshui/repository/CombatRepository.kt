package xyz.rtxux.game.shisanshui.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import xyz.rtxux.game.shisanshui.model.domain.CombatDO
import java.util.*
import javax.persistence.LockModeType

@Repository
interface CombatRepository : JpaRepository<CombatDO, Int> {
    //@Lock(LockModeType.READ)
    @Query("select c from CombatDO c where id in (select uc.id.combatId from UserCombatDO uc where not exists (select uc2 from UserCombatDO uc2 where uc2.id.combatId = uc.id.combatId and uc2.id.userId = :playerId) group by uc.id.combatId having count(uc.id.userId) < 4)")
    fun findAllAvailableRoomForPlayer(@Param("playerId") playerId: Int): List<CombatDO>

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from CombatDO c where c.id = ?1")
    fun findByIdForUpdate(id: Int): Optional<CombatDO>

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from CombatDO c where c.finishTime is null and exists (select uc.id.combatId from UserCombatDO uc where uc.id.combatId=c.id and uc.timestamp is not null group by uc.id.combatId having count(uc.id.userId)=4)")
    fun findAllFinishedNotCalculatedRoom(): List<CombatDO>
}