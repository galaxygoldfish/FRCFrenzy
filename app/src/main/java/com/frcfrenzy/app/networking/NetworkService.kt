package com.frcfrenzy.app.networking

import com.frcfrenzy.app.BuildConfig
import com.frcfrenzy.app.model.DistrictList
import com.frcfrenzy.app.model.EventItem
import com.frcfrenzy.app.model.EventList
import com.frcfrenzy.app.model.MatchList
import com.frcfrenzy.app.model.SeasonInformation
import com.frcfrenzy.app.model.TeamList
import com.tencent.mmkv.MMKV
import okhttp3.Credentials
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.Year

interface NetworkService {

    @GET("{season}")
    suspend fun getSeasonInfo(
        @Header("Authorization") basicAuth: String = getNetworkAuthCredentials(),
        @Path("season") season: Int = Year.now().value
    ) : SeasonInformation

    @GET("{season}/events")
    suspend fun getEventList(
        @Header("Authorization") basicAuth: String = getNetworkAuthCredentials(),
        @Path("season") season: Int = getDefaultYear(),
        @Query("excludeDistrict") excludeDistrictEvents: Boolean,
        @Query("teamNumber") teamNumber: Int? = null,
        @Query("districtCode") districtCode: String? = null,
        @Query("tournamentType") tournamentType: String? = null,
        @Query("weekNumber") weekNumber: Int? = null,
        @Query("eventCode") eventCode: String? = null
    ) : EventList

    @GET("{season}/districts")
    suspend fun getDistrictList(
        @Header("Authorization") basicAuth: String = getNetworkAuthCredentials(),
        @Path("season") season: Int = getDefaultYear()
    ) : DistrictList

    @GET("{season}/teams")
    suspend fun getTeamList(
        @Header("Authorization") basicAuth: String = getNetworkAuthCredentials(),
        @Path("season") season: Int = getDefaultYear(),
        @Query("teamNumber") teamNumber: Int? = null,
        @Query("eventCode") eventCode: String? = null,
        @Query("districtCode") districtCode: String? = null,
        @Query("page") page: Int? = null
    ) : TeamList

    @GET("{season}/matches/{eventCode}")
    suspend fun getEventSchedule(
        @Header("Authorization") basicAuth: String = getNetworkAuthCredentials(),
        @Path("season") season: Int = getDefaultYear(),
        @Path("eventCode") eventCode: String,
        @Query("tournamentLevel") tournamentLevel: String
    ) : MatchList

}

fun getNetworkAuthCredentials() : String = Credentials.basic(
    username = BuildConfig.NetworkUsername,
    password = BuildConfig.NetworkToken
)

fun getDefaultYear() : Int = Year.now().value