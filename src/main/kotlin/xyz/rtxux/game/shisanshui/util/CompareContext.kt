package xyz.rtxux.game.shisanshui.util

import xyz.rtxux.game.shisanshui.logic.CardConfig
import xyz.rtxux.game.shisanshui.model.domain.UserCombatDO

class CompareContext(
        private val userCombats: List<UserCombatDO>
) {
    data class UserCompareStatus(
            var score: Int = 0,
            var daQiang: MutableMap<UserCombatDO, Int> = HashMap<UserCombatDO, Int>()
    )

    private val status = HashMap<UserCombatDO, UserCompareStatus>()

    init {
        if (userCombats.size != 4) throw IllegalArgumentException()
        userCombats.forEach {
            status.put(it, UserCompareStatus())
        }
    }

    private fun compare(p1: UserCombatDO, p2: UserCombatDO) {
        val order = arrayOf(p1, p2)
        var tmpOrder: Array<UserCombatDO>
        if (p1.special != null && p2.special != null) {
            if (p1.special!! > p2.special!!) {
                tmpOrder = order
            } else if (p1.special!! < p2.special!!) {
                tmpOrder = swap(order)
            } else return
            val specialScore = CardConfig.getSpecialMultiple(tmpOrder[0].special!!)
            status[tmpOrder[0]]!!.score += specialScore
            status[tmpOrder[1]]!!.score -= specialScore
            status[tmpOrder[0]]!!.daQiang.put(tmpOrder[1], specialScore)
            return
        }
        if (p1.special != null || p2.special != null) {
            if (p2.special != null) {
                tmpOrder = swap(order)
            } else {
                tmpOrder = order
            }
            val specialScore = CardConfig.getSpecialMultiple(tmpOrder[0].special!!)
            status[tmpOrder[0]]!!.score += specialScore
            status[tmpOrder[1]]!!.score -= specialScore
            status[tmpOrder[0]]!!.daQiang.put(tmpOrder[1], specialScore)
            return
        }
        val scores = intArrayOf(0, 0, 0)
        val weight = arrayOf(arrayOf(p1.weight!![0], p1.weight!![1], p1.weight!![2]), arrayOf(p2.weight!![0], p2.weight!![1], p2.weight!![2]))
        for (i in 0..2) {
            if (weight[0][i] > weight[1][i]) {
                scores[i] = CardConfig.getMultiple(weight[0][i], i + 1)
            } else if (weight[0][i] < weight[1][i]) {
                scores[i] = -CardConfig.getMultiple(weight[1][i], i + 1)
            } else {
                scores[i] = 0
            }
            status[p1]!!.score += scores[i]
            status[p2]!!.score -= scores[i]

        }
        val sum = scores.reduce { acc, i -> acc + i }
        if (sum != 0 && scores[0] >= 0 && scores[1] >= 0 && scores[2] >= 0) {
            status[p1]!!.score += sum
            status[p2]!!.score -= sum
            if (scores[0] > 0 && scores[1] > 0 && scores[2] > 0) status[p1]!!.daQiang.put(p2, sum * 2)
        }
        if (sum != 0 && scores[0] <= 0 && scores[1] <= 0 && scores[2] <= 0) {
            status[p1]!!.score += sum
            status[p2]!!.score -= sum
            if (scores[0] < 0 && scores[1] < 0 && scores[2] < 0) status[p2]!!.daQiang.put(p1, -sum * 2)
        }
    }

    companion object {
        @JvmStatic
        private fun swap(order: Array<UserCombatDO>): Array<UserCombatDO> {
            val p1 = order[0]
            val p2 = order[1]
            return arrayOf(p2, p1)
        }
    }


}

