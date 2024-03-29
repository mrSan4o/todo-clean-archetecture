package com.san4o.just4fun.domain.core

import kotlinx.coroutines.*
import timber.log.Timber

object NoParams

open abstract class UseCase<in Param, out T>(
        protected val threads: CoroutineThreads = coroutinesThreads
) {

    abstract suspend fun run(param: Param): Result<T>

    var parentJob: Job = Job()

    operator fun invoke(param: Param, onResult: Result<T>.() -> Unit): Job {
        unsubscribe()
        val job = Job()
        this.parentJob = job
        CoroutineScope(threads.foregraound() + job).launch {
            try {
                val result = run(param)

                onResult(result)
            } catch (cancellationException: CancellationException) {
                Timber.e(cancellationException, "cancel invoke")
            } catch (e: Exception) {
                Timber.e(e, "invoke")
                onResult(Result.Failure(mapToDomainError(e)))
            }
        }
        return job
    }

    protected suspend fun <X> runBackground(context: CoroutineDispatcher = threads.background(), block: suspend () -> X): X {
        return withContext(context + this.parentJob) {
            block.invoke()
        }
    }

    protected suspend fun <X> async(context: CoroutineDispatcher = threads.background(), block: suspend () -> X): Deferred<X> {

        return CoroutineScope(context + this.parentJob)
                .async {
                    block.invoke()
                }
    }

    private fun mapToDomainError(e: Throwable): Error {
        return Error.ExecutionException(e)
    }

    fun unsubscribe() {
        parentJob.apply {
            cancelChildren()
            cancel()
        }
    }
}