package kz.home.pincode.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kz.home.pincode.domain.PinCode
import kz.home.pincode.domain.GetPinCodeUseCase

private const val KEY_PIN1 = "PIN"

class MainViewModel(
    val useCase: GetPinCodeUseCase
    , stateHandle: SavedStateHandle
) : ViewModel() {
    val pinLiveData = MutableLiveData<PinCode>()

    init {
        //start()
        val pin= stateHandle.get<PinCode>(KEY_PIN1)

        if (pin !=null) {
            pinLiveData.value = pin
        }else{
            start()
        }

        stateHandle.set(KEY_PIN1, pinLiveData.value)
    }

    private fun start() {
        pinLiveData.value = useCase.execute()
    }
}