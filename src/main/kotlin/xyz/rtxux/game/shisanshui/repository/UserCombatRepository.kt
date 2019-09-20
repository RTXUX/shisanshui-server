package xyz.rtxux.game.shisanshui.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import xyz.rtxux.game.shisanshui.model.UserCombatDO
import xyz.rtxux.game.shisanshui.model.UserCombatId

@Repository
interface UserCombatRepository : JpaRepository<UserCombatDO, UserCombatId>