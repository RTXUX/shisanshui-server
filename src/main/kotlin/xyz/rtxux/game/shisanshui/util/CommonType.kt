package xyz.rtxux.game.shisanshui.util

fun tongHuaShun(cards: List<Card>): Int {
    if (cards.size != 5)
        return 0
    val pointCount = IntArray(13) { i -> 0 }
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
    return begin + 4
}

fun zhaDan(cards: List<Card>): Int {
    if (cards.size != 5)
        return 0
    val pointCount = IntArray(13) { i -> 0 }
    cards.forEach {
        pointCount[it.point - 1]++
    }
    var boom = 0
    pointCount.forEachIndexed { index, i ->
        if (i == 4) {
            boom = index
        }
    }

    return boom
}

fun huLu(cards: List<Card>): Int {
    if (cards.size != 5)
        return 0
    val pointCount = IntArray(13) { i -> 0 }
    cards.forEach {
        pointCount[it.point - 1]++
    }
    var huLu3 = 0
    var huLu2 = 0
    pointCount.forEachIndexed { index, i ->
        if (i == 3) {
            huLu3 = index
        } else if (i == 2) {
            huLu2 = index
        }
    }
    if (huLu2 == 0) return 0
    return huLu3
}

fun tongHua(cards: List<Card>, isSp:Boolean = false): Int {
    if (cards.size != 5&&!isSp)
        return 0
    val pointCount = IntArray(13) { i -> 0 }
    var color = 0
    cards.forEach {
        pointCount[it.point - 1]++
        if (color == 0)
            color = it.color
        if (color != it.color)
            return 0
    }
    var hua = 0
    pointCount.forEachIndexed { index, i ->
        if (i != 0) {
            hua = index
        }
    }
    return hua
}

fun sunZi(cards: List<Card>): Int {
    if (cards.size != 5)
        return 0
    val pointCount = IntArray(13) { i -> 0 }
    cards.forEach {
        if (pointCount[it.point - 1] == 1) {
            return 0
        }
        pointCount[it.point - 1] = 1
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
    return begin + 4
}

fun sanTiao(cards: List<Card>): Int {
    val pointCount = IntArray(13) { i -> 0 }
    cards.forEach {
        pointCount[it.point - 1]++
    }
    var san = 0
    pointCount.forEachIndexed { index, i ->
        if (i == 3) {
            san = index
        }
    }
    return san
}

fun erDui(cards: List<Card>): Int {
    if (cards.size != 5)
        return 0
    val pointCount = IntArray(13) { i -> 0 }
    cards.forEach {
        pointCount[it.point - 1]++
    }
    var er = 0
    var num = 0
    pointCount.forEachIndexed { index, i ->
        if (i == 2) {
            if (index == er + 1)
                return 13 + er
            er = index
            num++
        }
    }
    if (num == 0) return 0
    return er
}

fun duiZi(cards: List<Card>): Int {
    val pointCount = IntArray(13) { i -> 0 }
    cards.forEach {
        pointCount[it.point - 1]++
    }
    var er = 0
    pointCount.forEachIndexed { index, i ->
        if (i == 2) {
            er = index
        }
    }
    return er
}

fun danPai(cards: List<Card>): Int {
    val pointCount = IntArray(13) { i -> 0 }
    cards.forEach {
        pointCount[it.point - 1]++
    }
    var dan = 0
    pointCount.forEachIndexed { index, i ->
        if (i == 2) {
            dan = index
        }
    }
    return dan
}