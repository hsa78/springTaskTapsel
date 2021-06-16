package com.example.springTask.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import springTask.AppStatistics
import java.util.*

@Component
class DataManager (private val statsRepository: StatsRepository) {
    fun getStats(startDate: Date, endDate: Date): ArrayList<AppStatistics> {
        val appStatistics: ArrayList<AppStatistics> = statsRepository.findByReportTimeBetweenOrderByReportTimeAsc(startDate, endDate)
        return appStatistics
    }
}