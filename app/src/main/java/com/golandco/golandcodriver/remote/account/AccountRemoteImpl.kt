package com.golandco.golandcodriver.remote.account

import com.golandco.golandcodriver.UserRole
import com.golandco.golandcodriver.data.account.AccountRemote
import com.golandco.golandcodriver.domain.exception.Either
import com.golandco.golandcodriver.domain.exception.Failure
import com.golandco.golandcodriver.domain.type.None
import com.golandco.golandcodriver.remote.core.Request
import com.golandco.golandcodriver.remote.service.ApiService
import javax.inject.Inject

/**
 * Для взаимодействия с аккаунтом в сети.
 */
class AccountRemoteImpl @Inject constructor(private val request: Request, private val service: ApiService) : AccountRemote {

    override fun register(
        call: String,
        country: String,
        dateOfBirth: String,
        email: String,
        password: String,
        phoneNumber: String,
        userRole: UserRole
    ): Either<Failure, None> {
        return request.make(service.register(createRegisterMap(call, country, dateOfBirth, email, password, phoneNumber, userRole))) {None()}
    }

    private fun createRegisterMap(
        call: String,
        country: String,
        dateOfBirth: String,
        email: String,
        password: String,
        phoneNumber: String,
        userRole: UserRole
    ): Map<String, String> {
        val  map = HashMap<String, String>()
        map.put(ApiService.PARAM_CALL, call)
        map.put(ApiService.PARAM_COUNTRY, country)
        map.put(ApiService.PARAM_USER_DATE, dateOfBirth)
        map.put(ApiService.PARAM_EMAIL, email)
        map.put(ApiService.PARAM_PASSWORD, password)
        map.put(ApiService.PARAM_PHONE_NUMBER, phoneNumber)
        map.put(ApiService.PARAM_USER_ROLE, UserRole.USER_ROLE.name)
        return map
    }


}