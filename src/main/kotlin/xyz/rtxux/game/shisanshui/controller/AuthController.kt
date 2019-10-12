package xyz.rtxux.game.shisanshui.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import xyz.rtxux.game.shisanshui.exception.AppException
import xyz.rtxux.game.shisanshui.model.domain.UserDO
import xyz.rtxux.game.shisanshui.model.dto.LoginDTO
import xyz.rtxux.game.shisanshui.model.dto.RegisterDTO
import xyz.rtxux.game.shisanshui.repository.UserRepository
import java.time.Instant
import java.util.concurrent.locks.ReentrantLock
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/auth")
class AuthController @Autowired constructor(
        val userRepository: UserRepository,
        val passwordEncoder: PasswordEncoder
) {
    val registerLock = ReentrantLock()

    @RequestMapping("/login", method = arrayOf(RequestMethod.POST))
    fun login(@RequestBody loginDTO: LoginDTO, request: HttpServletRequest): Map<String, Any> {
        val userOptioanl = userRepository.findUserDOByUsername(loginDTO.username!!)
        if (userOptioanl.isEmpty) {
            throw AppException("Username or password not match", null, 1005)
        }
        val user = userOptioanl.get()
        if (!passwordEncoder.matches(loginDTO.password, user.password)) {
            throw AppException("Username or password not match", null, 1005)
        }
        if (request.getSession(false) != null) request.getSession(false).invalidate()
        val session = request.getSession(true)
        SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(user.id, null, listOf(SimpleGrantedAuthority("USER"))) as Authentication?
        return mapOf(
                Pair("user_id", user.id!!),
                Pair("token", session.id)
        )
    }

    @RequestMapping("/validate")
    fun validate(@AuthenticationPrincipal userId: Int?): Map<String, Any> {
        if (userId == null) return mapOf(Pair("result", "Unauthorized"))
        val userOptional = userRepository.findById(userId)
        if (userOptional.isEmpty) throw AppException("Internal Server Error", null, 5000)
        return mapOf(
                Pair("user_id", userId)
        )
    }

    @RequestMapping("/logout", method = arrayOf(RequestMethod.POST))
    fun logout(request: HttpServletRequest): Map<String, Any> {
        SecurityContextHolder.clearContext()
        val session = request.getSession(false)
        session?.invalidate()
        return mapOf(
                Pair("result", "Success")
        )
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @RequestMapping("/register", method = arrayOf(RequestMethod.POST))
    fun register(@RequestBody registerDTO: RegisterDTO): Map<String, Any> {
        try {
            registerLock.lock()
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
        } catch (e: Throwable) {
            throw e
        } finally {
            registerLock.unlock()
        }
    }
}