package com.san4o.just4fun.domain

import com.san4o.just4fun.domain.core.Result
import com.san4o.just4fun.domain.core.UseCase
import com.san4o.just4fun.domain.model.Task
import com.san4o.just4fun.domain.model.TasksFilterType
import com.san4o.just4fun.domain.model.TasksFilterType.*
import com.san4o.just4fun.domain.repository.TasksRepository

data class GetTasksParams(
        val forceUpdate: Boolean = false,
        val currentFiltering: TasksFilterType = ALL_TASKS
)

class GetTasksUseCase(
        private val tasksRepository: TasksRepository
) : UseCase<GetTasksParams, List<Task>>() {

    override suspend fun run(param: GetTasksParams): Result<List<Task>> {
        val forceUpdate = param.forceUpdate
        val currentFiltering: TasksFilterType = param.currentFiltering

        val tasksResult = tasksRepository.getTasks(forceUpdate)

        // Filter tasks
        if (tasksResult is Result.Success && currentFiltering != ALL_TASKS) {
            val tasks = tasksResult.data

            val tasksToShow = mutableListOf<Task>()
            // We filter the tasks based on the requestType
            for (task in tasks) {
                when (currentFiltering) {
                    ACTIVE_TASKS -> if (task.isActive) {
                        tasksToShow.add(task)
                    }
                    COMPLETED_TASKS -> if (task.isCompleted) {
                        tasksToShow.add(task)
                    }
                    else -> throw NotImplementedError()
                }
            }
            return Result.Success(tasksToShow)
        }
        return tasksResult
    }

}