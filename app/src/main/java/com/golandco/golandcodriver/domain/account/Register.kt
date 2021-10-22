package com.golandco.golandcodriver.domain.account

import com.golandco.golandcodriver.UserRole
import com.golandco.golandcodriver.domain.exception.Either
import com.golandco.golandcodriver.domain.exception.Failure
import com.golandco.golandcodriver.domain.interactor.UseCase
import com.golandco.golandcodriver.domain.type.None
import javax.inject.Inject

/**
 * Для выполнения регистрации.
 */
class Register@Inject constructor(private val repository: AccountRepository): UseCase<None, Register.Params>() {

    override suspend fun run(params: Params): Either<Failure, None> {
        return repository.register(params.call, params.country, params.dateOfBirth, params.email, params.password, params.phoneNumber, params.userRole)
    }

    data class Params(val call: String, val country: String, val dateOfBirth: String, val email: String, val password: String, val phoneNumber: String, val userRole: UserRole)
}