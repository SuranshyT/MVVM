package kz.home.converter.data

import kz.home.converter.domain.Repository
import kz.home.converter.utils.Resource
import retrofit2.Retrofit
import java.lang.Exception

class RepositoryImpl(retrofit: Retrofit): Repository {

    private val apiService = retrofit.create(ApiService::class.java)

    override suspend fun getRates(): Resource<CurrencyRates> {
        return try {
            val response = apiService.getRates()
            val result = response.rates
            if(response.success) {
                Resource.Success(result)
            } else {
                Resource.Error("An error occurred")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }

    override suspend fun getNames(): Resource<CurrencyNames> {
        return try {
            val response = apiService.getNames()
            val result = response.symbols
            if(response.success) {
                Resource.Success(result)
            } else {
                Resource.Error("An error occurred")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }
}