package kz.home.converter.domain

import kz.home.converter.data.CurrencyNames
import kz.home.converter.data.CurrencyRates
import kz.home.converter.data.NamesResponse
import kz.home.converter.data.RatesResponse
import kz.home.converter.utils.Resource

interface Repository {

    suspend fun getRates():Resource<CurrencyRates>

    suspend fun getNames(): Resource<CurrencyNames>
}