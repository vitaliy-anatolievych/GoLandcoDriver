package com.golandco.golandcodriver.data.account

import com.golandco.golandcodriver.UserRole
import com.golandco.golandcodriver.domain.account.AccountEntity
import com.golandco.golandcodriver.domain.account.AccountRepository
import com.golandco.golandcodriver.domain.exception.Either
import com.golandco.golandcodriver.domain.exception.Failure
import com.golandco.golandcodriver.domain.exception.flatMap
import com.golandco.golandcodriver.domain.type.None

/**
 * Для взаимодействия с аккаунтом в бд и сети.
 */
class AccountRepositoryImpl(private val accountRemote: AccountRemote, private val accountCache: AccountCache): AccountRepository {
    override fun login(email: String, password: String): Either<Failure, AccountEntity> {
        throw UnsupportedOperationException("Login is not supported")
    }

    override fun logout(): Either<Failure, None> {
        throw UnsupportedOperationException("Logout is not supported")
    }

    override fun register(
        call: String,
        country: String,
        dateOfBirth: String,
        email: String,
        password: String,
        phoneNumber: String,
        userRole: UserRole
    ): Either<Failure, None> {
        return accountCache.getToken().flatMap {
            accountRemote.register(call, country, dateOfBirth, email, password, phoneNumber, userRole)
        }
    }

    override fun forgetPassword(email: String): Either<Failure, None> {
        throw UnsupportedOperationException("Password recovery is not supported")
    }

    override fun getCurrentAccount(): Either<Failure, AccountEntity> {
        throw UnsupportedOperationException("Get account is not supported")
    }

    override fun updateAccountToken(token: String): Either<Failure, None> {
        return accountCache.saveToken(token)
    }

    override fun updateAccountLastSeen(): Either<Failure, None> {
        throw UnsupportedOperationException("Updating last seen is not supported")
    }

    override fun editAccount(entity: AccountEntity): Either<Failure, AccountEntity> {
        throw UnsupportedOperationException("Editing account is not supported")
    }
}