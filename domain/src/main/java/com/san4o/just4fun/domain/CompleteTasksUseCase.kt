package com.san4o.just4fun.domain

import com.san4o.just4fun.domain.model.Task


class CompleteTaskUseCase(
        private val tasksRepository: TasksRepository
) {
    suspend operator fun invoke(task: Task) {

        tasksRepository.completeTask(task)
    }
}