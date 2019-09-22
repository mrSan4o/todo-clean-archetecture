package com.san4o.just4fun.domain

import com.san4o.just4fun.domain.model.Task


class SaveTaskUseCase(
        private val tasksRepository: TasksRepository
) {
    suspend operator fun invoke(task: Task) {

        return tasksRepository.saveTask(task)
    }

}