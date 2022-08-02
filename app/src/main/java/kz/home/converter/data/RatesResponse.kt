package kz.home.converter.data

data class RatesResponse(
    val base: String,
    val date: String,
    val rates: CurrencyRates,
    val success: Boolean,
    val timestamp: Int
)