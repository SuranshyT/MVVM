package kz.home.converter.data

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

const val API_KEY = "0oQuC6GT1z8eEgKUk8G4E8JSZNztJEiB"
const val SYMBOLS = "BTC%2CCHF%2CCNY%2CCZK%2CEGP%2CEUR%2CGBP%2CGEL%2CINR%2CJPY%2CKGS%2CKRW%2CKZT%2CNGN%2CRUB%2CSGD%2CTHB%2CTJS%2CTMT%2CTRY%2CUAH%2CUSD%2CUZS"
const val BASE = "KZT"

interface ApiService {

    @GET("latest")
    suspend fun getRates(
        @Header("apiKey") apiKey: String = API_KEY,
        @Query("symbols") symbols: String = SYMBOLS,
        @Query("base") base: String = BASE
    ): RatesResponse

    @GET("symbols")
    suspend fun getNames(
        @Header("apiKey") apiKey: String = API_KEY
    ): NamesResponse

}