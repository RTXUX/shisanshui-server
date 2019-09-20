package xyz.rtxux.game.shisanshui.model

import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "combat")
data class CombatDO(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int? = null,
        var finishTime: Instant? = null,
        @OneToMany(mappedBy = "combat")
        var users: List<UserCombatDO>? = null
)

