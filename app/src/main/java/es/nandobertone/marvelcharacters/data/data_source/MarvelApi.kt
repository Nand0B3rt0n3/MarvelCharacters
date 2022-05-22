package es.nandobertone.marvelcharacters.data.data_source

import es.nandobertone.marvelcharacters.data.data_source.dto.CharactersDTO
import es.nandobertone.marvelcharacters.util.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApi {

    @GET("/v1/public/characters")
    suspend fun getAllChatacters(
        @Query("apikey")apikey:String = Constants.API_KEY,
        @Query("ts")ts:String = Constants.timeStamp,
        @Query("hash")hash:String = Constants.hash(),
        @Query("offset")offset:String
    ):CharactersDTO

}