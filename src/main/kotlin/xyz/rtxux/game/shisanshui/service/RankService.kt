package xyz.rtxux.game.shisanshui.service

import org.springframework.stereotype.Service
import xyz.rtxux.game.shisanshui.model.dto.RankEntry

@Service
interface RankService {
    fun getRankData(): Iterable<RankEntry>
}