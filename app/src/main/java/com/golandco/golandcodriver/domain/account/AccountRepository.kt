package com.golandco.golandcodriver.domain.account

import com.golandco.golandcodriver.UserRole
import com.golandco.golandcodriver.domain.exception.Either
import com.golandco.golandcodriver.domain.exception.Failure
import com.golandco.golandcodriver.domain.type.None

/**
 *
 * Интерфейс, содержащий функции для взаимодействия с аккаунтом.
 *
 */
interface AccountRepository {
    fun login(email: String, password: String): Either<Failure, AccountEntity>
    fun logout(): Either<Failure, None>
    fun register(call: String, country: String, dateOfBirth: String, email: String, password: String, phoneNumber: String, userRole: UserRole): Either<Failure, None>
    fun forgetPassword(email: String): Either<Failure, None>

    fun getCurrentAccount(): Either<Failure, AccountEntity>

    fun updateAccountToken(token: String): Either<Failure, None>
    fun updateAccountLastSeen(): Either<Failure, None>

    fun editAccount(entity: AccountEntity): Either<Failure, AccountEntity>
}