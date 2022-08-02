package kz.home.converter.presentation.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {
    val chipLiveData = MutableLiveData<MutableList<String>>()

    init {
        //start()
    }

    private fun start() {
        chipLiveData.value = mutableListOf()
    }
}