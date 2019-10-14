package xyz.rtxux.game.shisanshui.service

import xyz.rtxux.game.shisanshui.model.dto.RegisterDTO

interface AuthService {
    fun register(registerDTO: RegisterDTO, studentNumber: String? = null): Map<String, Any>

    fun authJwc(studentNumber: String, password: String): String
}