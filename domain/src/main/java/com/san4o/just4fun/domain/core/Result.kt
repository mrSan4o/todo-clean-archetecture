/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.san4o.just4fun.domain.core

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Failure(val error: FailureResult) : Result<Nothing>()


    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Failure -> "Failure[error=$error]"

            else -> "Unknown"
        }
    }

    fun handle(onSuccess: (R) -> Unit,
               onError: (FailureResult) -> Unit) {
        when (this) {
            is Success<R> -> onSuccess(data)
            is Failure -> onError(error)
        }
    }
}

sealed class FailureResult {
    data class Error(val message: String = "Error") : FailureResult()

    open class FeatureError(val exception: Throwable = Exception("Fail"))
}

/**
 * `true` if [Result] is of type [Success] & holds non-null [Success.data].
 */
val Result<*>.succeeded
    get() = this is Result.Success && data != null


public inline fun <T, R> T.catchedExecution(block: T.() -> R): Result<R> {
    return try {
        Result.Success(block())
    } catch (e: Throwable) {
        Result.Failure(FailureResult.Error(e.message ?: "null"))
    }
}