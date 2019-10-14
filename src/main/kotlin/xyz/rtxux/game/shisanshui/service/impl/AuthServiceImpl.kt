package xyz.rtxux.game.shisanshui.service.impl

import com.github.kittinunf.fuel.Fuel
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

    companion object {
        val jwcUrl = "http://59.77.226.32/logincheck.asp"
    }
    @Transactional
    override fun register(registerDTO: RegisterDTO, studentNumber: String?): Map<String, Any> {
        val userOptional = userRepository.findUserDOByUsername(registerDTO.username);
        if (!userOptional.isEmpty) {
            throw AppException("Username already registered", null, 1001)
        }
        val user = UserDO(
                username = registerDTO.username,
                password = passwordEncoder.encode(registerDTO.password),
                studentNumber = studentNumber,
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

    override fun authJwc(studentNumber: String, password: String): String {
        val (_, response, result) = Fuel.post(jwcUrl, listOf(
                "muser" to studentNumber,
                "passwd" to password,
                "x" to "4",
                "y" to "4"
        )).allowRedirects(false).responseString()
        if (response.statusCode == 302) return studentNumber
        throw AppException("Failed to authenticate with jiaowuchu", null, 1003)
    }
}