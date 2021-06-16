package com.example.springTask.logic

import com.example.springTask.repository.DataManager
import com.example.springTask.repository.StatsRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component
import springTask.AppStatistics
import springTask.services.responses.AppStatisticsListResponse
import springTask.services.responses.AppStatisticsModel
import java.util.*
import kotlin.collections.ArrayList


@Component
class Tapsel(private val statsRepository: StatsRepository) {
    private val dataManager: DataManager = DataManager(statsRepository)

    fun getStatsFormDataManager(startDate: Date, endDate: Date, type: Int): ArrayList<AppStatistics> {
        val statisticsBetweenStartAndEndTime: ArrayList<AppStatistics> = dataManager.getStats(startDate, endDate)
        val requestedStatistics: ArrayList<AppStatistics> = ArrayList()
        for (appStatistic in statisticsBetweenStartAndEndTime){
            if (appStatistic.type == type)
                requestedStatistics.add(appStatistic)
        }
        return requestedStatistics
    }

    fun getYearAndWeekNum(date: Date): Pair<Int,Int> {
        val cal = JalaliCalendar(date.year, date.month, date.day)
        val year = cal[Calendar.YEAR]
        val weekNum = cal[Calendar.WEEK_OF_YEAR]
        return Pair(year,weekNum)
    }

    fun convertAppStatisticsToOutputModels(startDate: Date, endDate: Date, appStatistics: ArrayList<AppStatistics>):
                                            AppStatisticsListResponse{

        val startYearAndWeek = getYearAndWeekNum(startDate)
        val endYearAndWeek = getYearAndWeekNum(endDate)
        val iterator = appStatistics.toList().iterator()
        var currYearAndWeekNum = startYearAndWeek
        var currAppStatistics: AppStatistics
        var appYearAndWeekNum:Pair<Int,Int>
        var model = AppStatisticsModel(currYearAndWeekNum.first, currYearAndWeekNum.second)
        var result = ArrayList<AppStatisticsModel>()

        //core of service logic is this part.
        // iterate over 2 loop: one for appstatistics and one for creating model based on week
        while(true){
            currAppStatistics = iterator.next()
            appYearAndWeekNum = getYearAndWeekNum(currAppStatistics.reportTime)

            while( !(model.year == appYearAndWeekNum.first && model.weekNum == appYearAndWeekNum.second)) {
                result.add(model)
                currYearAndWeekNum = getNextYearAndWeekNum(currYearAndWeekNum.first, currYearAndWeekNum.second)
                model = AppStatisticsModel(currYearAndWeekNum.first, currYearAndWeekNum.second)
            }
            model.incClicks(currAppStatistics.videoClicks + currAppStatistics.webViewClicks)
            model.incInstalls(currAppStatistics.videoInstalls + currAppStatistics.webViewInstalls)
            model.incRequests(currAppStatistics.videoRequests + currAppStatistics.webViewRequests)

            if(!iterator.hasNext()){
                while(currYearAndWeekNum != endYearAndWeek){
                    result.add(model)
                    currYearAndWeekNum = getNextYearAndWeekNum(currYearAndWeekNum.first, currYearAndWeekNum.second)
                    model = AppStatisticsModel(currYearAndWeekNum.first, currYearAndWeekNum.second)
                }
                break
            }

        }
        return AppStatisticsListResponse(result)
    }

    private fun getNextYearAndWeekNum(year: Int, weekNum: Int): Pair<Int, Int> {
        if(weekNum < 53){
            return Pair(year, weekNum + 1)
        }
        return Pair(year + 1, 1)
    }

    @Cacheable("statsCache")
    fun getStats(startDate: Date, endDate: Date, type: Int): AppStatisticsListResponse {
        val appStatistics:ArrayList<AppStatistics> = getStatsFormDataManager(startDate, endDate, type)
        return convertAppStatisticsToOutputModels(startDate, endDate, appStatistics)

    }
}