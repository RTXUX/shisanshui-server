package xyz.rtxux.game.shisanshui.util

import xyz.rtxux.game.shisanshui.model.Card
import xyz.rtxux.game.shisanshui.model.CardType

fun check(cards: List<Card>): CardType {

    val sp = checkSp(cards)
    if (sp.rank != 0) return sp

    return checkCommon(cards)
}

fun checkCommon(card: List<Card>): CardType {

    var ans = tongHuaShun(card)
    if (ans != 0) return CardType(9, ans)

    ans = zhaDan(card)
    if (ans != 0) return CardType(8, ans)

    ans = huLu(card)
    if (ans != 0) return CardType(7, ans)

    ans = tongHua(card)
    if (ans != 0) return CardType(6, ans)

    ans = sunZi(card)
    if (ans != 0) return CardType(5, ans)

    ans = sanTiao(card)
    if (ans != 0) return CardType(4, ans)

    ans = erDui(card)
    if (ans != 0) return CardType(3, ans)

    ans = duiZi(card)
    if (ans != 0) return CardType(2, ans)

    ans = danPai(card)
    if (ans != 0) return CardType(1, ans)

    return CardType(0, 0)
}


fun checkSp(card: List<Card>): CardType {

    if (card.size != 13) return CardType(0, 0)

    var ans = zhiZunQinLong(card)
    if (ans != 0) return CardType(23, ans)

    ans = yiTiaoLong(card)
    if (ans != 0) return CardType(22, ans)

    ans = shiErHuangZhu(card)
    if (ans != 0) return CardType(21, ans)

    ans = sanTongHuaSun(card)
    if (ans != 0) return CardType(20, ans)

    ans = sanFenTianXia(card)
    if (ans != 0) return CardType(19, ans)

    ans = quanDa(card)
    if (ans != 0) return CardType(18, ans)

    ans = quanXiao(card)
    if (ans != 0) return CardType(17, ans)

    ans = couYiSe(card)
    if (ans != 0) return CardType(16, ans)

    ans = shuangGuaiCongSan(card)
    if (ans != 0) return CardType(15, ans)

    ans = siTaoSanTiao(card)
    if (ans != 0) return CardType(14, ans)

    ans = wuDuiSanTiao(card)
    if (ans != 0) return CardType(13, ans)

    ans = liuDuiBan(card)
    if (ans != 0) return CardType(12, ans)

    ans = sanSunZI(card)
    if (ans != 0) return CardType(11, ans)

    ans = sanTongHua(card)
    if (ans != 0) return CardType(10, ans)

    return CardType(0, 0)
}
