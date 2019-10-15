package xyz.rtxux.game.shisanshui.repository

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import xyz.rtxux.game.shisanshui.model.domain.UserCombatDO
import xyz.rtxux.game.shisanshui.model.domain.UserCombatId

@Repository
interface UserCombatRepository : JpaRepository<UserCombatDO, UserCombatId> {
    fun findAllByIdCombatId(combatId: Int): List<UserCombatDO>

    @Query("select uc from UserCombatDO uc where uc.id.userId=:id and uc.timestamp is not null")
    fun findAllByIdUserId(id: Int, pageable: Pageable): List<UserCombatDO>

    @Modifying
    @Query(value = "update user_combat set weight=ARRAY[0,0,0], timestamp=now() where extract(epoch from age(start_time))>10 and timestamp is null", nativeQuery = true)
    fun markZero()
}