package xyz.rtxux.game.shisanshui.obsolete

fun countCommonShui(type: CardType, round: Int): Int {
    if (type.rank == 0) return 0
    if (type.rank < 7) return 1
    if (type.rank == 7) return roundCount(round)
    if (type.rank == 8) return roundCount(round) * 4
    if (type.rank == 9) return roundCount(round) * 5
    //TODO
    return countSpShui(type)
}

fun roundCount(rounds: Int): Int {
    if (rounds == 2) return 2
    if (rounds == 3) return 1
    return 0
}

fun countSpShui(type: CardType): Int {
    if (type.rank==10)return 3
    if (type.rank==11)return 3
    if (type.rank==13)return 5
    if (type.rank==14)return 6
    if (type.rank==15)return 8
    if (type.rank==16)return 10
    if (type.rank==17)return 12
    if (type.rank==18)return 15
    if (type.rank==19)return 20
    if (type.rank==20)return 22
    if (type.rank==21)return 24
    if (type.rank==22)return 26
    if (type.rank==23)return 52
    return 0
}