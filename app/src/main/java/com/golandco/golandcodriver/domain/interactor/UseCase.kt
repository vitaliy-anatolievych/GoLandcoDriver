package com.golandco.golandcodriver.domain.interactor

import com.golandco.golandcodriver.domain.exception.Either
import com.golandco.golandcodriver.domain.exception.Failure
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

abstract class UseCase<out Type, in Params> {
    var backgroundContext: CoroutineContext = Dispatchers.IO
    var foregroundContext: CoroutineContext = Dispatchers.Main

    private var parentJob: Job = Job()

    abstract suspend fun run(params: Params): Either<Failure, Type>

    operator fun invoke(params: Params, onResult: (Either<Failure, Type>) -> Unit = {}) {
        unsubscribe()
        parentJob = Job()

        CoroutineScope(foregroundContext + parentJob).launch {
            val result = withContext(backgroundContext) {
                run(params)
            }

            onResult(result)
        }
    }

    fun unsubscribe() {
        parentJob.apply {
            cancelChildren()
            cancel()
        }
    }
}