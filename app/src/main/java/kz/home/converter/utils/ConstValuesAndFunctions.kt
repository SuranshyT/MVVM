package kz.home.converter.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import kz.home.converter.domain.Currency
import kz.home.converter.domain.Flag
import kz.home.converter.domain.News

var allCurrenciesList = mutableListOf<Currency>()
var addedCurrenciesList = mutableListOf<Currency>()
val chipsList: MutableList<String> = mutableListOf()
var inputValue: String = "0"
var menuStatus = 1

val newsList = mutableListOf(
    News(
        "Как приручить дракона (2010)",
        "https://avatars.mds.yandex.net/get-zen_doc/4719986/pub_60b8ce7c97fbac40dba6c609_60b8cfaf336a83035159e36c/scale_1200",
        "Вы узнаете историю подростка Иккинга, которому не слишком близки традиции его героического племени, много лет ведущего войну с драконами. Мир Иккинга переворачивается с ног на голову, когда он неожиданно встречает дракона Беззубика, который поможет ему и другим викингам увидеть привычный мир с совершенно другой стороны…"
    ),
    News(
        "Паразиты (2019)",
        "https://avatars.mds.yandex.net/get-kino-vod-films-gallery/1668876/2443657f8d924d747337268e5910fdfd/orig",
        "Обычное корейское семейство Кимов жизнь не балует. Приходится жить в сыром грязном полуподвале, воровать интернет у соседей и перебиваться случайными подработками. Однажды друг сына семейства, уезжая на стажировку за границу, предлагает тому заменить его и поработать репетитором у старшеклассницы в богатой семье Пак. Подделав диплом о высшем образовании, парень отправляется в шикарный дизайнерский особняк и производит на хозяйку дома хорошее впечатление. Тут же ему в голову приходит необычный план по трудоустройству сестры"
    ),
    News(
        "Унесённые призраками (2001)",
        "https://www.kino-teatr.ru/video/14149/start.jpg",
        "Тихиро с мамой и папой переезжает в новый дом. Заблудившись по дороге, они оказываются в странном пустынном городе, где их ждет великолепный пир. Родители с жадностью набрасываются на еду и к ужасу девочки превращаются в свиней, став пленниками злой колдуньи Юбабы. Теперь, оказавшись одна среди волшебных существ и загадочных видений, Тихиро должна придумать, как избавить своих родителей от чар коварной старухи"
    )
)

val flagList = listOf(
    Flag("BTC","https://upload.wikimedia.org/wikipedia/commons/thumb/4/46/Bitcoin.svg/800px-Bitcoin.svg.png"),
    Flag("CHF","https://upload.wikimedia.org/wikipedia/commons/thumb/f/f2/Civil_Ensign_of_Switzerland.svg/1500px-Civil_Ensign_of_Switzerland.svg.png"),
    Flag("CNY","https://upload.wikimedia.org/wikipedia/commons/2/2e/Flag_of_China.png"),
    Flag("CZK","https://upload.wikimedia.org/wikipedia/commons/thumb/c/cb/Flag_of_the_Czech_Republic.svg/800px-Flag_of_the_Czech_Republic.svg.png?20110920204324"),
    Flag("EGP","https://upload.wikimedia.org/wikipedia/commons/thumb/f/fe/Flag_of_Egypt.svg/2560px-Flag_of_Egypt.svg.png"),
    Flag("EUR","https://upload.wikimedia.org/wikipedia/commons/thumb/b/b7/Flag_of_Europe.svg/2560px-Flag_of_Europe.svg.png"),
    Flag("GBP","https://upload.wikimedia.org/wikipedia/commons/thumb/4/42/Flag_of_the_United_Kingdom.png/1200px-Flag_of_the_United_Kingdom.png"),
    Flag("GEL","https://upload.wikimedia.org/wikipedia/commons/thumb/a/a2/Flag_of_Kingdom_of_Georgia.svg/1500px-Flag_of_Kingdom_of_Georgia.svg.png"),
    Flag("INR","https://upload.wikimedia.org/wikipedia/commons/b/bc/Flag_of_India.png"),
    Flag("JPY","https://upload.wikimedia.org/wikipedia/commons/thumb/9/9e/Flag_of_Japan.svg/2560px-Flag_of_Japan.svg.png"),
    Flag("KGS","https://upload.wikimedia.org/wikipedia/commons/thumb/c/c7/Flag_of_Kyrgyzstan.svg/1280px-Flag_of_Kyrgyzstan.svg.png"),
    Flag("KRW","https://upload.wikimedia.org/wikipedia/commons/0/0f/Flag_of_South_Korea.png"),
    Flag("KZT","https://upload.wikimedia.org/wikipedia/commons/thumb/d/d3/Flag_of_Kazakhstan.svg/2560px-Flag_of_Kazakhstan.svg.png"),
    Flag("NGN","https://upload.wikimedia.org/wikipedia/commons/b/b6/Flag_of_Nigeria.png"),
    Flag("RUB","https://upload.wikimedia.org/wikipedia/commons/d/d4/Flag_of_Russia.png"),
    Flag("SGD","https://upload.wikimedia.org/wikipedia/commons/thumb/4/48/Flag_of_Singapore.svg/2560px-Flag_of_Singapore.svg.png"),
    Flag("THB","https://upload.wikimedia.org/wikipedia/commons/thumb/a/a9/Flag_of_Thailand.svg/2560px-Flag_of_Thailand.svg.png"),
    Flag("TJS","https://upload.wikimedia.org/wikipedia/commons/c/ca/Flag_of_Tajikistan.PNG"),
    Flag("TMT","https://upload.wikimedia.org/wikipedia/commons/thumb/1/1b/Flag_of_Turkmenistan.svg/2560px-Flag_of_Turkmenistan.svg.png"),
    Flag("TRY","https://upload.wikimedia.org/wikipedia/commons/thumb/b/b4/Flag_of_Turkey.svg/800px-Flag_of_Turkey.svg.png?20210808085121"),
    Flag("UAH","https://upload.wikimedia.org/wikipedia/commons/thumb/d/d2/Flag_of_Ukraine.png/640px-Flag_of_Ukraine.png"),
    Flag("USD","https://upload.wikimedia.org/wikipedia/en/thumb/a/a4/Flag_of_the_United_States.svg/2560px-Flag_of_the_United_States.svg.png"),
    Flag("UZS","https://upload.wikimedia.org/wikipedia/commons/thumb/0/0b/Flag_of_Uzbekistan.png/1200px-Flag_of_Uzbekistan.png")
)

fun randomID(): String = List(4) {
    (('a'..'z') + ('A'..'Z') + ('0'..'9')).random()
}.joinToString("")

fun View.hideKeyboard() {
    val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(windowToken, 0)
}