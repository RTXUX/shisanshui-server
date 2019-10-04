package xyz.rtxux.game.shisanshui.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import xyz.rtxux.game.shisanshui.model.domain.CombatDO

@Repository
interface CombatRepository : JpaRepository<CombatDO, Int> {
    @Query("select c from combat c where id in (select combat_id from user_combat uc where uc.player_id!=:playerId group by combat_id having count(*) < 4)")
    fun findAllAvailableRoomForPlayer(@Param("playerId") playerId: Int): List<CombatDO>
}