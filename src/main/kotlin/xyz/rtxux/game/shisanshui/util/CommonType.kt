package xyz.rtxux.game.shisanshui.util

import xyz.rtxux.game.shisanshui.model.Card

fun tongHuaShun(cards: List<Card>): Int {
    if (cards.size != 5)
        return 0
    val pointCount = IntArray(13, { i -> 0 })
    var colorCount = 0
    cards.forEach {
        if (pointCount[it.point - 1] == 1) {
            return 0
        }
        pointCount[it.point - 1] = 1
        if (colorCount == 0) {
            colorCount = it.color
        }
        if (colorCount != it.color)
            return 0
    }
    var begin = 0
    pointCount.forEachIndexed { index, i ->
        if (begin == 0 && i != 0) {
            begin = index
        }
        if (begin != 0 && i == 0 && index - begin < 5) {
            return 0
        }
    }
    return begin + 4 + 1
}

fun zhaDan(cards: List<Card>): Int {
    if (cards.size != 5)
        return 0
    val pointCount = IntArray(13, { i -> 0 })
    cards.forEach {
        pointCount[it.point - 1]++;
    }
    var boom = 0
    pointCount.forEachIndexed { index, i ->
        if (i == 4) {
            boom = index
        }
    }
    return boom + 1
}