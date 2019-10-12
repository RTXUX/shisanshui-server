package xyz.rtxux.game.shisanshui.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional
import xyz.rtxux.game.shisanshui.model.dto.RankEntry
import xyz.rtxux.game.shisanshui.repository.UserRepository
import xyz.rtxux.game.shisanshui.service.RankService
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

@Service
class RankServiceImpl @Autowired constructor(
        private val userRepository: UserRepository
) : RankService {
    private val listLock = ReentrantReadWriteLock()
    private var rankList: List<RankEntry> = listOf()

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Scheduled(cron = "0 * * * * *")
    fun updateRankList() {
        val users = userRepository.findAll()
        val ranks = users.map {
            RankEntry(
                    player_id = it.id!!,
                    score = it.score!!,
                    name = it.username!!
            )
        }.sortedByDescending { it.score }.toList()
        listLock.write {
            rankList = ranks
        }
    }

    override fun getRankData(): Iterable<RankEntry> {
        return listLock.read {
            rankList
        }
    }
}