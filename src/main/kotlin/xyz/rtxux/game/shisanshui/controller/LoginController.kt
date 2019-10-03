package xyz.rtxux.game.shisanshui.controller

import org.springframework.beans.factory.annotation.Autowired
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
import xyz.rtxux.game.shisanshui.model.dto.LoginDTO
import xyz.rtxux.game.shisanshui.repository.UserRepository
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/auth")
class LoginController @Autowired constructor(
        val userRepository: UserRepository,
        val passwordEncoder: PasswordEncoder
) {

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
        SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(user.id, null, listOf(SimpleGrantedAuthority("USER")))
        return mapOf(
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
}