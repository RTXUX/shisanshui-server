package xyz.rtxux.game.shisanshui.model.domain

import com.vladmihalcea.hibernate.type.array.IntArrayType
import com.vladmihalcea.hibernate.type.array.StringArrayType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import org.hibernate.annotations.TypeDefs
import java.io.Serializable
import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "user_combat")
@TypeDefs(
        TypeDef(name = "string-array", typeClass = StringArrayType::class),
        TypeDef(name = "int-array", typeClass = IntArrayType::class)
)
data class UserCombatDO(
        @EmbeddedId
        var id: UserCombatId? = null,
        @ManyToOne(fetch = FetchType.EAGER)
        @MapsId("userId")
        var user: UserDO? = null,
        @ManyToOne(fetch = FetchType.EAGER)
        @MapsId("combatId")
        var combat: CombatDO? = null,
        @Type(type = "string-array")
        @Column(columnDefinition = "text[]")
        var card: Array<String>? = null,
        @Type(type = "int-array")
        @Column(columnDefinition = "integer[]")
        var weight: Array<Int>? = null,
        var originalScore: Int? = null,
        var deltaScore: Int? = null,
        var timestamp: Instant? = null
)

@Embeddable
data class UserCombatId(
        var userId: Int? = null,
        var combatId: Int? = null
) : Serializable
