package xyz.rtxux.game.shisanshui.util

import org.junit.Test
import xyz.rtxux.game.shisanshui.obsolete.shiErHuangZhu
import xyz.rtxux.game.shisanshui.obsolete.strToCards
import xyz.rtxux.game.shisanshui.obsolete.yiTiaoLong
import xyz.rtxux.game.shisanshui.obsolete.zhiZunQinLong

class SpTypeUtilTests {
    @Test
    fun testZhiZunQinLong() {
        assert(1 == zhiZunQinLong(strToCards("*2*3*4*5*6*7*8*9*10*j*q*k*a")))
        assert(1 == zhiZunQinLong(strToCards("*2*3*4*5*6*7*8*9*10*j*q*k*a")))
        assert(0 == zhiZunQinLong(strToCards("*2*3*4*6*6*7*8*9*10*j*q*k*a")))
        assert(0 == zhiZunQinLong(strToCards("#2*3*4*6*6*7*8*9*10*j*q*k*a")))
        assert(0 == zhiZunQinLong(strToCards("#2*3*4*5*6*7*8*9*10*j*q*k*a")))
        assert(0 == zhiZunQinLong(strToCards("#2*3*4*5*6*8*8*9*10\$j*q*k*a")))
    }

    @Test
    fun testYiTiaoLong() {
        assert(1 == yiTiaoLong(strToCards("*2*3*4*5*6*7*8*9*10*j*q*k*a")))
        assert(1 == yiTiaoLong(strToCards("*2*3*4*5*6*7*8*9*10*j*q*k*a")))
        assert(0 == yiTiaoLong(strToCards("*2*3*4*6*6*7*8*9*10*j*q*k*a")))
        assert(0 == yiTiaoLong(strToCards("#2*3*4*6*6*7*8*9*10*j*q*k*a")))
        assert(1 == yiTiaoLong(strToCards("*2$3*4*5*6*7*8*9*10*j*q*k*a")))
        assert(0 == yiTiaoLong(strToCards("#2*3*4*5*6*8*8*9*10\$j*q*k*a")))
    }

    @Test
    fun testShiErHuangZhu() {
        assert(1 == shiErHuangZhu(strToCards("*q*k*k*k*a*a*a*q*q*j*q*k*a")))
        assert(0 == shiErHuangZhu(strToCards("*1*1*1*1*j*j*j*q*q*j*q*k*a")))
        assert(0 == shiErHuangZhu(strToCards("*1*1*1*1*j*j*j*q&q*j*q*k*a")))
        assert(0 == shiErHuangZhu(strToCards("*1*1*1*1*j*j*j*q\$q*j*q*k*a")))
        assert(0 == shiErHuangZhu(strToCards("*1*1*1*1*j*j*j*q#q*j*q*k*a")))
        assert(0 == shiErHuangZhu(strToCards("*1*1*1*1*j*j*j*q#q*j*q*k*a")))
        assert(0 == shiErHuangZhu(strToCards("*1*1*1*9*j*j*j*q#q*j*q*k*a")))

    }
}