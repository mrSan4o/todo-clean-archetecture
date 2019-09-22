package com.san4o.just4fun.domain

class DeleteTaskUseCase(
        private val tasksRepository: TasksRepository
) {
    suspend operator fun invoke(taskId: String) {

        return tasksRepository.deleteTask(taskId)
    }

}