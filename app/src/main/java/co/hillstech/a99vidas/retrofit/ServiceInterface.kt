package co.hillstech.a99vidas.retrofit

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceInterface {
    @GET("v1/getGames/")
    fun getGames(@Query("user") user: Int): Observable<MutableList<Game>>

    @GET("v1/setVote/")
    fun setVote(@Query("user") user: Int,
                @Query("rating") rating: Int,
                @Query("game") game: Int,
                @Query("type") type: Int): Observable<Vote>

    @GET("v1/getVote/")
    fun getVote(@Query("user") user: Int,
                @Query("game") game: Int): Observable<Vote>
}