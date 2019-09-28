package com.san4o.just4fun.domain

import com.san4o.just4fun.domain.core.Result
import com.san4o.just4fun.domain.model.Task
import com.san4o.just4fun.domain.task.GetTaskParams

class TaskInteractor(
        private val repository: TasksRepository
) {

    suspend fun getTasks(
            params: GetTaskParams
    ): Result<List<Task>> {
        return repository.getTasks(params.forceUpdate)
    }

    suspend fun saveTask(task: Task) {
        return repository.saveTask(task)
    }

    suspend fun getTask(taskId: String, forceUpdate: Boolean = false): Result<Task> {
        return repository.getTask(taskId, forceUpdate)
    }


    suspend fun deleteTask(taskId: String) {
        return repository.deleteTask(taskId)
    }

    suspend fun complete(task: Task) {
        repository.completeTask(task)
    }

    suspend fun active(task: Task) {
        repository.activateTask(task)
    }

    suspend fun clearCompletedTasks() {
        repository.clearCompletedTasks()
    }
}