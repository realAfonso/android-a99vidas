package co.hillstech.almanaque99vidas.retrofit

import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceInterface {
    @GET("v1/getGames/")
    fun getGames(@Query("user") user: Int):MutableList<Game>

    @GET("v1/setVote/")
    fun setVote(@Query("user") user: Int,
                @Query("rating") rating: Int,
                @Query("game") game: Int,
                @Query("type") type: Int): Vote

    @GET("v1/getVote/")
    fun getVote(@Query("user") user: Int,
                @Query("game") game: Int): Vote
}