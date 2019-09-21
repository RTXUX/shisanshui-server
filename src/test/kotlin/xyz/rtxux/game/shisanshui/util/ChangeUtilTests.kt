package xyz.rtxux.game.shisanshui.util

import org.junit.Test
import xyz.rtxux.game.shisanshui.model.Card

class ChangeUtilTests {
    @Test
    fun testStrToPoint() {
        assert(strToPoint("2") == 1)
        assert(strToPoint("3") == 2)
        assert(strToPoint("4") == 3)
        assert(strToPoint("5") == 4)
        assert(strToPoint("6") == 5)
        assert(strToPoint("7") == 6)
        assert(strToPoint("8") == 7)
        assert(strToPoint("9") == 8)
        assert(strToPoint("1") == 9)
        assert(strToPoint("0") == 9)
        assert(strToPoint("10") == 9)
        assert(strToPoint("J") == 10)
        assert(strToPoint("j") == 10)
        assert(strToPoint("Q") == 11)
        assert(strToPoint("q") == 11)
        assert(strToPoint("k") == 12)
        assert(strToPoint("K") == 12)
        assert(strToPoint("A") == 13)
        assert(strToPoint("a") == 13)
    }

    @Test
    fun testCardsToStr() {
        assert(cardsToStr(listOf("*1", "$2", "*3")) == "*1$2*3")
        assert(cardsToStr(listOf("*1")) == "*1")
        assert(cardsToStr(listOf("*1", "*2", "*3")) == "*1*2*3")
        assert(cardsToStr(listOf("*1", "*A", "*3")) == "*1*A*3")
    }

    @Test
    fun testStrToCards() {
        assert((listOf(Card(9, 3), Card(1, 1), Card(2, 3)) == strToCards("*1$2*3")))
        assert((listOf(Card(9, 3), Card(1, 1), Card(2, 3)) == strToCards("*10$2*3")))
        assert((listOf(Card(9, 3), Card(1, 1), Card(2, 3)) == strToCards("*1$2*3")))
    }

    @Test
    fun testCardToPoint() {
        assert(cardToPoint("*2") == 1)
        assert(cardToPoint("#2") == 1)
        assert(cardToPoint("*10") == 9)
        assert(cardToPoint("&2") == 1)
        assert(cardToPoint("$2") == 1)
        assert(cardToPoint("*A") == 13)
        assert(cardToPoint("*J") == 10)

    }
}