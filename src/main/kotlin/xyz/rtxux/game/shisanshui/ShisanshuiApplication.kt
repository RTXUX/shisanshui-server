package xyz.rtxux.game.shisanshui

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
class ShisanshuiApplication

fun main(args: Array<String>) {
    runApplication<ShisanshuiApplication>(*args)
}
