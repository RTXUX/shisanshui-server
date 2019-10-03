package xyz.rtxux.game.shisanshui.model.domain

import java.time.Instant
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "user")
data class UserDO(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @NotNull
        var id: Int? = null,
        @NotNull
        var username: String? = null,
        @NotNull
        var password: String? = null,
        @NotNull
        var studentNumber: String? = null,
        @NotNull
        var createdAt: Instant? = null,
        @NotNull
        var score: Int? = null,
        @NotNull
        var combatNumber: Int? = null,
        @OneToMany(mappedBy = "user")
        var combats: List<UserCombatDO>? = null
)