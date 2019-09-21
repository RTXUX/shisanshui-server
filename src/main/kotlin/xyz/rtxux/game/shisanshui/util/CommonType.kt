package xyz.rtxux.game.shisanshui.util

import xyz.rtxux.game.shisanshui.model.Card

fun tongHuaShun(cards: List<Card>): Int {
    if (cards.size != 5)
        return 0
    val pointCount = IntArray(13)
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
    return 1
}