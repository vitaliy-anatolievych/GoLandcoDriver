package com.golandco.golandcodriver.domain.exception

sealed class Failure {
    object NetworkConnectionError: Failure()
    object ServerError: Failure()
}
