package xyz.rtxux.game.shisanshui

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import xyz.rtxux.game.shisanshui.model.domain.UserDO
import xyz.rtxux.game.shisanshui.repository.UserRepository
import java.time.Instant

@SpringBootApplication
@EnableJpaRepositories
class ShisanshuiApplication

fun main(args: Array<String>) {
    runApplication<ShisanshuiApplication>(*args)
}

@Component
class DataLoader @Autowired constructor(
        val userRepository: UserRepository,
        val passwordEncoder: PasswordEncoder
) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        userRepository.save(UserDO(
                id = 1,
                username = "test",
                password = passwordEncoder.encode("test"),
                studentNumber = "0",
                createdAt = Instant.now(),
                score = 0,
                combatNumber = 0
        ))
    }
}
