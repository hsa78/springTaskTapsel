package springTask

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
data class AppStatistics(
        @Id
        val id : String,
        val reportTime : Date,
        val type : Int,
        val videoRequests : Int,
        val webViewRequests : Int,
        val videoClicks : Int,
        val webViewClicks : Int,
        val videoInstalls : Int,
        val webViewInstalls : Int) {

}