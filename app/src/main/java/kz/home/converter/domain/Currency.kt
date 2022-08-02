package kz.home.converter.domain

data class Currency(
    val id: String,
    var name: String,
    val symbol: String,
    var img: String,
    var conversionRate: Double = 0.0,
    var value: Double = 0.0
)