package kz.home.pincode.data

import kz.home.pincode.domain.PinCode

class PinCodeMapper {
    fun map(data: PinCodeResponse): PinCode {
        return PinCode(data.value)
    }
}