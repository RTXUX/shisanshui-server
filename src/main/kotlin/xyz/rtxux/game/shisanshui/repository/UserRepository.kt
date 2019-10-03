package xyz.rtxux.game.shisanshui.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import xyz.rtxux.game.shisanshui.model.domain.UserDO
import java.util.*

@Repository
interface UserRepository : JpaRepository<UserDO, Int> {
    fun findUserDOByUsername(username: String): Optional<UserDO>
}