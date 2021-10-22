package com.golandco.golandcodriver.cache

import com.golandco.golandcodriver.data.account.AccountCache
import com.golandco.golandcodriver.domain.exception.Either
import com.golandco.golandcodriver.domain.exception.Failure
import com.golandco.golandcodriver.domain.type.None
import javax.inject.Inject

/**
 * Для взаимодействия с аккаунтом в бд.
 */
class AccountCacheImpl @Inject constructor(private val prefsManager: SharedPrefsManager): AccountCache {

    override fun saveToken(token: String): Either<Failure, None> {
        return prefsManager.saveToken(token)
    }

    override fun getToken(): Either<Failure, String> {
        return prefsManager.getToken()
    }
}