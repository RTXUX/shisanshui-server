package xyz.rtxux.game.shisanshui.obsolete

data class Card(val point: Int, val color: Int) {
    override fun toString(): String {
        return "${colorToStr(color)}${pointToStr(point)}"
    }
}