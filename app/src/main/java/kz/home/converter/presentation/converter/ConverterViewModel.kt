package kz.home.converter.presentation.converter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kz.home.converter.domain.Currency
import kz.home.converter.domain.Repository
import kz.home.converter.utils.*

class ConverterViewModel(
    private val repository: Repository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    var list = MutableLiveData<MutableList<Currency>>()

    fun getCurrencies() {
        viewModelScope.launch(ioDispatcher) {
            when(val ratesResponse = repository.getRates()) {
                is Resource.Success -> {
                    val rates = ratesResponse.data
                    val rateFields = rates!!::class.java.declaredFields

                    for (f in rateFields) {
                        f.isAccessible = true
                        allCurrenciesList.add(Currency(randomID(), "" ,f.name, "", f.get(rates) as Double))
                    }

                    for (i in allCurrenciesList.indices) {
                        if (allCurrenciesList[i].symbol == flagList[i].name) {
                            allCurrenciesList[i].img = flagList[i].image
                        }
                    }

                    when(val namesResponse = repository.getNames()) {
                        is Resource.Success -> {
                            val names = namesResponse.data
                            val nameFields = names!!::class.java.declaredFields

                            for (f in nameFields) {
                                f.isAccessible = true
                                for (i in allCurrenciesList.indices) {
                                    if (f.name.equals(allCurrenciesList[i].symbol)) {
                                        allCurrenciesList[i].name = f.get(names) as String
                                    }
                                }
                            }
                        }
                        else -> {}
                    }
                }
                else -> {}
            }
        }
    }

    fun addCurrency(item: Currency) {
        addedCurrenciesList.add(item)
        list.value = addedCurrenciesList
    }

    fun calculateConvertation() {
        for (i in addedCurrenciesList.indices) {
            addedCurrenciesList[i].value = (inputValue.toDouble() * addedCurrenciesList[i].conversionRate)
        }
    }

}