package xyz.rtxux.game.shisanshui.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import xyz.rtxux.game.shisanshui.exception.AppException
import xyz.rtxux.game.shisanshui.model.domain.UserDO
import xyz.rtxux.game.shisanshui.model.dto.RegisterDTO
import xyz.rtxux.game.shisanshui.repository.UserRepository
import xyz.rtxux.game.shisanshui.service.AuthService
import java.time.Instant

@Service
class AuthServiceImpl @Autowired constructor(
        private val userRepository: UserRepository,
        private val passwordEncoder: PasswordEncoder
) : AuthService {
    @Transactional
    override fun register(registerDTO: RegisterDTO): Map<String, Any> {
        val userOptional = userRepository.findUserDOByUsername(registerDTO.username);
        if (!userOptional.isEmpty) {
            throw AppException("Username already registered", null, 1001)
        }
        val user = UserDO(
                username = registerDTO.username,
                password = passwordEncoder.encode(registerDTO.password),
                studentNumber = null,
                createdAt = Instant.now(),
                score = 0,
                combatNumber = null
        )
        val savedUser = userRepository.save(user)
        return mapOf(
                "msg" to "Success",
                "user_id" to savedUser.id!!
        )
    }
}