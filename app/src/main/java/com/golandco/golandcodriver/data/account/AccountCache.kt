package com.golandco.golandcodriver.data.account

import com.golandco.golandcodriver.domain.exception.Either
import com.golandco.golandcodriver.domain.exception.Failure
import com.golandco.golandcodriver.domain.type.None

/**
 * Интерфейс, содержащий функции для взаимодействия с аккаунтом в локальной базе данных.
 */
interface AccountCache {
    fun getToken(): Either<Failure, String>
    fun saveToken(token: String): Either<Failure, None>
}