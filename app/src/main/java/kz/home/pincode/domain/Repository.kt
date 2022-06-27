package kz.home.pincode.domain

interface Repository {
    fun getPin(): PinCode
}