package com.golandco.golandcodriver.domain.account

import com.golandco.golandcodriver.domain.exception.Either
import com.golandco.golandcodriver.domain.exception.Failure
import com.golandco.golandcodriver.domain.interactor.UseCase
import com.golandco.golandcodriver.domain.type.None
import javax.inject.Inject

/**
 * Для выполнения обновления токена.
 */
class UpdateToken @Inject constructor(private val accountRepository: AccountRepository): UseCase<None, UpdateToken.Params>() {
    override suspend fun run(params: Params): Either<Failure, None> = accountRepository.updateAccountToken(params.token)

    data class Params(val token: String)
}
