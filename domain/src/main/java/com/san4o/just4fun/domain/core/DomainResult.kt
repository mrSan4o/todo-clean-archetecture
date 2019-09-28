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

sealed class DomainResult<out R, out E> {

    data class Success<out T>(val data: T) : DomainResult<T, Nothing>()
    data class Failure<out E>(val error: E) : DomainResult<Nothing, E>()

    fun fold(onSuccess: (R) -> Unit, onError: (E) -> Unit) {
        when (this) {
            is Success<R> -> onSuccess(data)
            is Failure -> onError(error)
        }
    }

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Failure -> "Failure[error=$error]"

            else -> "Unknown"
        }
    }
}
