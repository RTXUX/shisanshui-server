package xyz.rtxux.game.shisanshui.service

import xyz.rtxux.game.shisanshui.model.dto.RegisterDTO

interface AuthService {
    fun register(registerDTO: RegisterDTO): Map<String, Any>
}