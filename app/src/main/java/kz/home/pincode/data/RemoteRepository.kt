package kz.home.pincode.data

import kz.home.pincode.domain.PinCode
import kz.home.pincode.domain.Repository

class RemoteRepository(
    private val mapper: PinCodeMapper
) : Repository {
    override fun getPin(): PinCode {
        val webResponse = PinCodeResponse("")

        return  mapper.map(webResponse)
    }
}