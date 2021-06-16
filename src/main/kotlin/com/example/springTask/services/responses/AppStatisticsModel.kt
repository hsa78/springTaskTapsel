package springTask.services.responses

class AppStatisticsModel(
        val weekNum : Int,
        val year : Int,
        ) {
    private var requests : Int = 0;
    private var clicks : Int = 0;
    private var installs : Int = 0;

    fun incRequests(value: Int){
        requests += value;
    }

    fun incClicks(value: Int){
        clicks += value;
    }

    fun incInstalls(value: Int){
        installs += value;
    }
}