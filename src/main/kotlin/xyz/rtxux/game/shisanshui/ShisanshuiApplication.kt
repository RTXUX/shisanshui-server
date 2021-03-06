package xyz.rtxux.game.shisanshui

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.transaction.support.AbstractPlatformTransactionManager
import xyz.rtxux.game.shisanshui.logic.HelpUtil
import xyz.rtxux.game.shisanshui.repository.UserRepository

@SpringBootApplication
@EnableJpaRepositories
class ShisanshuiApplication

fun main(args: Array<String>) {
    runApplication<ShisanshuiApplication>(*args)
}

@Component
class DataLoader @Autowired constructor(
        val userRepository: UserRepository,
        val passwordEncoder: PasswordEncoder,
        val transactionManager: AbstractPlatformTransactionManager
) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        HelpUtil.initCache()
        transactionManager.isNestedTransactionAllowed = true
//        try {
//            userRepository.save(UserDO(
//                    id = 1,
//                    username = "test1",
//                    password = passwordEncoder.encode("test1"),
//                    studentNumber = "0",
//                    createdAt = Instant.now(),
//                    score = 0,
//                    combatNumber = 0
//            ))
//        } catch (e: Throwable) {
//            println(e)
//        }
//
//        userRepository.save(UserDO(
//                id = 2,
//                username = "test2",
//                password = passwordEncoder.encode("test2"),
//                studentNumber = "0",
//                createdAt = Instant.now(),
//                score = 0,
//                combatNumber = 0
//        ))
//        userRepository.save(UserDO(
//                id = 3,
//                username = "test3",
//                password = passwordEncoder.encode("test3"),
//                studentNumber = "0",
//                createdAt = Instant.now(),
//                score = 0,
//                combatNumber = 0
//        ))
//        userRepository.save(UserDO(
//                id = 4,
//                username = "test4",
//                password = passwordEncoder.encode("test4"),
//                studentNumber = "0",
//                createdAt = Instant.now(),
//                score = 0,
//                combatNumber = 0
//        ))
    }
}
