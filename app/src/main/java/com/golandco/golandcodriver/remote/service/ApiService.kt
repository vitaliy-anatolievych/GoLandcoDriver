package com.golandco.golandcodriver.remote.service

import com.golandco.golandcodriver.remote.core.BaseResponse
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Для формирования API запросов.
 */
interface ApiService {
    companion object {
    //methods
    const val REGISTER = "/api/core/auth/register"

    //params
    const val PARAM_CALL = "call"
    const val PARAM_COUNTRY = "country"
    const val PARAM_USER_DATE = "dateOfBirth"
    const val PARAM_EMAIL = "email"
    const val PARAM_PASSWORD = "password"
    const val PARAM_PHONE_NUMBER = "phoneNumber"
    const val PARAM_USER_ROLE = "userRole"
}

    @FormUrlEncoded
    @POST(REGISTER)
    fun register(@FieldMap params: Map<String, String>): Call<BaseResponse>
}