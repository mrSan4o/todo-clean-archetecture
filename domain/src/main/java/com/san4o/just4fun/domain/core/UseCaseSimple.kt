package com.san4o.just4fun.domain.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


abstract class ScopeUseCase<out Type, in Params> where Type : Any {

    abstract suspend fun run(params: Params): Result<Type>

    open operator fun invoke(
            scope: CoroutineScope,
            params: Params,
            onResult: (Result<Type>) -> Unit = {}
    ) {
        val backgroundJob = scope.async { run(params) }
        scope.launch { onResult(backgroundJob.await()) }
    }
}


abstract class SimpleUseCase<out Type, in Params>(
        private val threads: CoroutineThreads = coroutinesThreads
) where Type : Any {

    abstract suspend fun run(params: Params): Result<Type>

    open operator fun invoke(params: Params, onResult: (Result<Type>) -> Unit = {}) {
        val job = CoroutineScope(threads.background()).async { run(params) }
        CoroutineScope(threads.foregraound()).launch { onResult(job.await()) }
    }
}