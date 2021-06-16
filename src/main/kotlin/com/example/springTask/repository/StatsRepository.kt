package com.example.springTask.repository

import org.springframework.cache.annotation.Cacheable
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.repository.MongoRepository
import springTask.AppStatistics
import java.util.*
import kotlin.collections.ArrayList


interface StatsRepository: MongoRepository<AppStatistics, String> {

    fun findByReportTimeBetweenOrderByReportTimeAsc(startDate: Date, endDate: Date): ArrayList<AppStatistics>
}