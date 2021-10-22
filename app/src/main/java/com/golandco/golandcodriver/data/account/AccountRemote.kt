package com.golandco.golandcodriver.data.account

import com.golandco.golandcodriver.UserRole
import com.golandco.golandcodriver.domain.exception.Either
import com.golandco.golandcodriver.domain.exception.Failure
import com.golandco.golandcodriver.domain.type.None

/**
 * Интерфейс, содержащий функции для взаимодействия с аккаунтом на сервере.
 */

interface AccountRemote {
    fun register(
        call: String,
        country: String,
        dateOfBirth: String,
        email: String,
        password: String,
        phoneNumber: String,
        userRole: UserRole
    ): Either<Failure, None>

}