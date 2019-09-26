package com.san4o.just4fun.domain.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface CoroutineThreads {
    fun background(): CoroutineDispatcher
    fun foregraound(): CoroutineDispatcher
    fun copmutation(): CoroutineDispatcher
}

val coroutinesThreads = AppCoroutineThreads()

class AppCoroutineThreads : CoroutineThreads {
    override fun background() = Dispatchers.IO

    override fun foregraound() = Dispatchers.Main

    override fun copmutation() = Dispatchers.Default
}