package xyz.rtxux.game.shisanshui.exception

class AppException(message: String?, override val cause: Throwable?, val status: Int) : RuntimeException(message, cause)