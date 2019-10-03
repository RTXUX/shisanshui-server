package xyz.rtxux.game.shisanshui.util

import org.junit.Test
import xyz.rtxux.game.shisanshui.obsolete.checkCommon
import xyz.rtxux.game.shisanshui.obsolete.strToCards

class TypeUtilTests {
    @Test
    fun testCheckCommon() {
        println(checkCommon(strToCards("*2*3*4*5*6")))
    }
}