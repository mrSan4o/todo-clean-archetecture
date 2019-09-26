package com.san4o.just4fun.domain.task

import com.san4o.just4fun.domain.TasksRepository
import com.san4o.just4fun.domain.core.CoroutineThreads
import com.san4o.just4fun.domain.core.Result
import com.san4o.just4fun.domain.core.coroutinesThreads
import com.san4o.just4fun.domain.model.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class TaskInteractor(
        private val repository: TasksRepository,
        private val threads: CoroutineThreads = coroutinesThreads
) {

    fun getTasks(

            params: GetTaskParams,
            onResult: (Result<List<Task>>) -> Unit
    ): Job {
        return invoke({ repository.getTasks(params.forceUpdate) }, onResult)
    }

    protected fun <Type> invoke(
            run: suspend () -> Result<Type>,
            onResult: (Result<Type>) -> Unit = {}
    ): Job {
        val job = CoroutineScope(threads.background())
                .async { run() }
        return CoroutineScope(threads.foregraound())
                .launch { onResult(job.await()) }
    }

}