package com.san4o.just4fun.domain

class ClearCompletedTasksUseCase(
        private val tasksRepository: TasksRepository
) {
    suspend operator fun invoke() {

        tasksRepository.clearCompletedTasks()
    }
}