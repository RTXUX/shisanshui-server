package xyz.rtxux.game.shisanshui.util

import xyz.rtxux.game.shisanshui.model.Card

fun zhiZunQinLong(cards: List<Card>): Int {
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
    return 1
}


fun yiTiaoLong(cards: List<Card>): Int {
    val pointCount = IntArray(13)
    cards.forEach {
        if (pointCount[it.point - 1] == 1) {
            return 0
        }
        pointCount[it.point - 1] = 1
    }
    return 1
}

fun shiErHuangZhu(cards: List<Card>): Int {
    var below10Count = 0
    cards.forEach {
        if (it.point < 10) {
            if (below10Count++ > 0) {
                print(below10Count)
                return 0
            }
        }
    }
    return 1
}

fun sanTongHuaSun(cards: List<Card>): Int {
    //TODO
    return 0
}

fun sanFenTianXia(cards: List<Card>): Int {
    val hash = HashMap<Int, Int>()
    cards.forEach {
        hash[it.point] = (hash[it.point] ?: 0) + 1
    }
    if (hash.size != 4) {
        return 0
    }
    var flag = false
    hash.forEach { (_, v) ->
        flag = flag || v != 4 && v != 1
    }
    if (flag)
        return 0
    return 1
}

fun quanDa(cards: List<Card>): Int {
    cards.forEach {
        if (it.point < 8) {
            return 0
        }
    }
    return 1
}

fun quanXiao(cards: List<Card>): Int {
    cards.forEach {
        if (it.point > 8) {
            return 0
        }
    }
    return 1
}

fun couYiSe(cards: List<Card>): Int {
    val colorCount = IntArray(2)
    cards.forEach {
        colorCount[((it.color - 1) / 2 + 1) % 2] = 1
        if (colorCount[(it.color - 1) / 2] == 1) {
            return 0
        }
    }
    return 1
}

fun shuangGuaiCongSan(cards: List<Card>): Int {
    //TODO
    return 0
}

fun siTaoSanTiao(cards: List<Card>): Int {
    val hash = HashMap<Int, Int>()
    cards.forEach {
        hash[it.point] = (hash[it.point] ?: 0) + 1
    }
    if (hash.size != 5) {
        return 0
    }
    var flag = false
    hash.forEach { (_, v) ->
        flag = flag || v != 3 && v != 1
    }
    if (flag)
        return 0
    return 1
}


//TODO
fun wuDuiSanTiao(cards: List<Card>): Int {
    val hash = HashMap<Int, Int>()
    cards.forEach {
        hash[it.point] = (hash[it.point] ?: 0) + 1
    }
    if (hash.size > 6) {
        return 0
    }
    var flag = false
    hash.forEach { (_, v) ->
        flag = flag || v != 3 && v != 2
    }
    if (flag)
        return 0
    return 1
}

//TODO
fun liuDuiBan(cards: List<Card>): Int {
    val hash = HashMap<Int, Int>()
    cards.forEach {
        hash[it.point] = (hash[it.point] ?: 0) + 1
    }
    if (hash.size > 7) {
        return 0
    }
    var flag = false
    hash.forEach { (_, v) ->
        flag = flag || v != 2 && v != 1
    }
    if (flag)
        return 0
    return 1
}

fun sanSunZI(cards: List<Card>): Int {
    cardTo3(cards).forEach {
        if (it.size == 3) {
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
                if (begin != 0 && i == 0 && index - begin < 3) {
                    return 0
                }
            }
        } else if (it.size == 5) {
            if (sunZi(it) == 0) return 0
        } else {
            return 0
        }
    }
    return 1
}

fun sanTongHua(cards: List<Card>): Int {
    cardTo3(cards).forEach {
        if (tongHua(it)==0)return 0
    }
    return 1
}
