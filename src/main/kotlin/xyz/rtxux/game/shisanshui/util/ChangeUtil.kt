package xyz.rtxux.game.shisanshui.util


import water.exception.CardNumException
import water.exception.FormatException
import xyz.rtxux.game.shisanshui.model.Card

val cardToPointRegex = Regex("[*&#\$]([1-9JQKAjqka])")

fun cardToPoint(card: String): Int {
    val result = cardToPointRegex.find(card) ?: throw FormatException(card)
    return strToPoint(result.groupValues[1])
}

fun cardsToPoint(cards: List<String>): List<Int> {
    return cards.map {
        val result = cardToPointRegex.find(it) ?: throw FormatException(it)
        strToPoint(result.value)
    }
}

val cardToColorRegex = Regex("([*&#\$])[1-9JQKAjqka]")

fun cardToColor(card: String): Int {
    val result = cardToColorRegex.find(card) ?: throw FormatException(card)
    return strToPoint(result.groupValues[1])
}

fun cardsToColor(cards: List<String>): List<Int> {
    return cards.map {
        val result = cardToColorRegex.find(it) ?: throw FormatException(it)
        strToPoint(result.value)
    }
}

val strToCardsRegex = Regex("([*&#\$])([1-9JQKAjqka])")

fun strToCards(str: String): List<Card> {
    return strToCardsRegex.findAll(str).toList().map {
        Card(strToPoint(it.groupValues[2]), strToColor(it.groupValues[1]))
    }
}

fun cardsToStr(cards: List<String>): String {
    return cards.joinToString("")
}

fun strToPoint(str: String): Int {
    when (str) {
        "A" -> return 13
        "a" -> return 13
        "K" -> return 12
        "k" -> return 12
        "Q" -> return 11
        "q" -> return 11
        "J" -> return 10
        "j" -> return 10
        "1" -> return 9
        "0" -> return 9
        "10" -> return 9
        "9" -> return 8
        "8" -> return 7
        "7" -> return 6
        "6" -> return 5
        "5" -> return 4
        "4" -> return 3
        "3" -> return 2
        "2" -> return 1
        else -> throw FormatException(str)
    }
}

fun strToColor(str: String): Int {
    when (str) {
        "$" -> return 1
        "&" -> return 2
        "*" -> return 3
        "#" -> return 4
        else -> throw FormatException(str)
    }
}

fun pointToStr(point: Int): String {
    when (point) {
        1 -> return "2"
        2 -> return "3"
        3 -> return "4"
        4 -> return "5"
        5 -> return "6"
        6 -> return "7"
        7 -> return "8"
        8 -> return "9"
        9 -> return "10"
        10 -> return "J"
        11 -> return "Q"
        12 -> return "K"
        13 -> return "A"
        else -> throw FormatException(point.toString())
    }
}


fun check(cards: List<Card>, num: Int) {
    if (cards.size != num) throw CardNumException(num, cards.size)
}

fun check13(cards: List<Card>) {
    check(cards, 13)
}

fun check5(cards: List<Card>) {
    check(cards, 5)
}

fun check3(cards: List<Card>) {
    check(cards, 3)
}

fun cardTo3(cards: List<Card>): List<List<Card>> {
    val ans = Array<ArrayList<Card>>(3) { i -> arrayListOf() }
    cards.forEachIndexed { index, card ->
        if (index < 3) ans[0].add(card)
        if (index < 8) ans[1].add(card)
        if (index < 13) ans[2].add(card)
    }
    return ans.map {
        it.toList()
    }
}

fun cardTo1(cards: List<List<Card>>): List<Card> {
    val ans = arrayListOf<Card>()
    cards.map {
        it.map {
            ans.add(it)
        }
    }
    return ans.toList()
}