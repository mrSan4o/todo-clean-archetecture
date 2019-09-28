package com.san4o.just4fun.domain.core

import timber.log.Timber

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Failure(val error: Error) : Result<Nothing>()


    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Failure -> "Failure[error=$error]"

            else -> "Unknown"
        }
    }

    fun handle(onSuccess: (R) -> Unit,
               onError: (Error) -> Unit) {
        when (this) {
            is Success<R> -> onSuccess(data)
            is Error -> onError(this)
        }
    }
}

sealed class Error {
    object Fail : Error()
    open class Message(val text: String) : Error()
    open class ExecutionException(val exception: Throwable) : Error()
}

fun Error.buidMessage(exceptionMessage: (Error.ExecutionException) -> String = { "Ошибка: ${it.exception.message}" }): String {
    val e = this
    return when (e) {
        Error.Fail -> "Ошибка"
        is Error.Message -> e.text
        is Error.ExecutionException -> exceptionMessage.invoke(e)
        else -> "Ошибка"
    }
}

public inline fun <T, R> T.runCatching(run: T.() -> R): Result<R> {
    return try {
        Result.Success(run())
    } catch (e: Throwable) {
        Timber.e(e, "runCatching")
        Result.Failure(Error.ExecutionException(e))
    }
}