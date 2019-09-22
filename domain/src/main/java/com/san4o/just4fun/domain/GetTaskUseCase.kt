package com.san4o.just4fun.domain

import com.san4o.just4fun.domain.core.Result
import com.san4o.just4fun.domain.model.Task


class GetTaskUseCase(
    private val tasksRepository: TasksRepository
) {
    suspend operator fun invoke(taskId: String, forceUpdate: Boolean = false): Result<Task> {

            return tasksRepository.getTask(taskId, forceUpdate)
    }

}