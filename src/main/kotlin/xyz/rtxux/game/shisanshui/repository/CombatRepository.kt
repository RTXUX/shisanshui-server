package xyz.rtxux.game.shisanshui.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import xyz.rtxux.game.shisanshui.model.domain.CombatDO

@Repository
interface CombatRepository : JpaRepository<CombatDO, Int> {
    @Query("select c from combat c where id in (select combat_id from user_combat uc where not exists (select uc2 from user_combat uc2 where uc2.combat_id = uc.combat_id and uc2.user_id = :playerId) group by combat_id having count(combat_id) < 4)")
    fun findAllAvailableRoomForPlayer(@Param("playerId") playerId: Int): List<CombatDO>
}