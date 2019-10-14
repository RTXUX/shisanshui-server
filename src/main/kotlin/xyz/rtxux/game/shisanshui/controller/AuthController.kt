package xyz.rtxux.game.shisanshui.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import xyz.rtxux.game.shisanshui.exception.AppException
import xyz.rtxux.game.shisanshui.model.dto.BindDTO
import xyz.rtxux.game.shisanshui.model.dto.LoginDTO
import xyz.rtxux.game.shisanshui.model.dto.RegisterDTO
import xyz.rtxux.game.shisanshui.repository.UserRepository
import xyz.rtxux.game.shisanshui.service.AuthService
import java.util.concurrent.locks.ReentrantLock
import javax.servlet.http.HttpServletRequest
import javax.transaction.Transactional

@RestController
@RequestMapping("/auth")
class AuthController @Autowired constructor(
        private val userRepository: UserRepository,
        private val passwordEncoder: PasswordEncoder,
        private val authService: AuthService
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
        val authoritiesList = mutableListOf<SimpleGrantedAuthority>(SimpleGrantedAuthority("USER"))
        if (user.studentNumber != null) authoritiesList.add(SimpleGrantedAuthority("AUTH2"))
        SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(user.id, null, authoritiesList)
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

    @RequestMapping("/register", method = arrayOf(RequestMethod.POST))
    fun register(@RequestBody registerDTO: RegisterDTO): Map<String, Any> {
        try {
            registerLock.lock()
            return authService.register(registerDTO)
        } catch (e: Throwable) {
            throw e
        } finally {
            registerLock.unlock()
        }
    }

    @Transactional
    @RequestMapping("/register2", method = arrayOf(RequestMethod.POST))
    fun register2(@RequestBody registerDTO: RegisterDTO): Map<String, Any> {
        try {
            registerLock.lock()
            val studentNumber = authService.authJwc(registerDTO.studentNumber!!, registerDTO.studentPassword!!)
            if (userRepository.findByStudentNumber(studentNumber).isPresent) throw AppException("Student Number used", null, 1002)
            return authService.register(registerDTO, studentNumber)
        } catch (e: Throwable) {
            throw e
        } finally {
            registerLock.unlock()
        }
    }

    @Transactional
    @RequestMapping("/bind", method = arrayOf(RequestMethod.POST))
    @PreAuthorize("hasAuthority('USER')")
    fun bind(@RequestBody bindDTO: BindDTO, @AuthenticationPrincipal userId: Int?): Map<String, Any> {
        try {
            registerLock.lock()
            if (userId == null) throw AppException("User not authenticated", null, 5000)
            val studentNumber = authService.authJwc(bindDTO.studentNumber, bindDTO.studentPassword)
            val user = userRepository.findById(userId).orElseThrow { AppException("something went wrong", null, 5000) }
            if (user.studentNumber != null && user.studentNumber != studentNumber) throw AppException("Already bind", null, 1002)
            user.studentNumber = studentNumber
            userRepository.save(user)
            return mapOf(
                    "msg" to "Success"
            )
        } catch (e: Throwable) {
            throw e
        } finally {
            registerLock.unlock()
        }
    }
}