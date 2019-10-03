package xyz.rtxux.game.shisanshui.model.domain

import java.io.Serializable
import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "user_combat")
data class UserCombatDO(
        @EmbeddedId
        var id: UserCombatId? = null,
        @ManyToOne(fetch = FetchType.EAGER)
        @MapsId("userId")
        var user: UserDO? = null,
        @ManyToOne(fetch = FetchType.EAGER)
        @MapsId("combatId")
        var combat: CombatDO? = null,
        var card: String? = null,
        var originalScore: Int? = null,
        var deltaScore: Int? = null,
        var timestamp: Instant? = null
)

@Embeddable
data class UserCombatId(
        var userId: Int? = null,
        var combatId: Int? = null
) : Serializable
