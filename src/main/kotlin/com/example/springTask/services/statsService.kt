package springTask.services

import com.example.springTask.logic.Tapsel
import com.example.springTask.repository.StatsRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import springTask.services.responses.AppStatisticsListResponse
import java.util.*


@RestController
class statsService(private val statsRepository: StatsRepository) {
    private val tapsel = Tapsel(statsRepository)

    @RequestMapping(value = ["/stats"], method = [RequestMethod.GET])
    fun getStats(@RequestAttribute("startDate") startDate: Date,
                 @RequestAttribute("endDate") endDate: Date,
                 @RequestAttribute("type") type: Int): ResponseEntity<AppStatisticsListResponse>? {

        val stats: AppStatisticsListResponse = tapsel.getStats(startDate, endDate, type)
        return ResponseEntity.ok(stats)
    }

}