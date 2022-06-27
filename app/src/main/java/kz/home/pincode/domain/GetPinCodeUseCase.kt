package kz.home.pincode.domain

class GetPinCodeUseCase(
    private val repository: Repository
) {
    fun execute(): PinCode = repository.getPin()
}