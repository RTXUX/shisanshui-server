package xyz.rtxux.game.shisanshui.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import xyz.rtxux.game.shisanshui.model.dto.RankEntry
import xyz.rtxux.game.shisanshui.service.RankService
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/rank")
class RankController @Autowired constructor(
        private val rankService: RankService
) {
    @RequestMapping("", method = arrayOf(RequestMethod.GET))
    fun getRankData(response: HttpServletResponse) {
        response.sendRedirect("/rank/rank.json")
    }

    @RequestMapping("/rank.json", method = arrayOf(RequestMethod.GET))
    fun tmpGetRealRankData(): Iterable<RankEntry> {
        return rankService.getRankData()
    }
}