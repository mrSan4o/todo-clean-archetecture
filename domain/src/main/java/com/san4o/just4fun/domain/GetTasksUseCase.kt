package com.san4o.just4fun.domain

import com.san4o.just4fun.domain.core.Result
import com.san4o.just4fun.domain.core.Result.Success
import com.san4o.just4fun.domain.model.Task
import com.san4o.just4fun.domain.model.TasksFilterType
import com.san4o.just4fun.domain.model.TasksFilterType.*

class GetTasksUseCase(
        private val tasksRepository: TasksRepository
) {
    suspend operator fun invoke(
            forceUpdate: Boolean = false,
            currentFiltering: TasksFilterType = ALL_TASKS
    ): Result<List<Task>> {


        val tasksResult = tasksRepository.getTasks(forceUpdate)

        // Filter tasks
        if (tasksResult is Success && currentFiltering != ALL_TASKS) {
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
                    else -> NotImplementedError()
                }
            }
            return Success(tasksToShow)
        }
        return tasksResult
    }

}