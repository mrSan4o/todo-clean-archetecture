package com.san4o.just4fun.data

import com.example.android.architecture.blueprints.todoapp.data.TaskEntity
import com.san4o.just4fun.domain.model.Task


fun TaskEntity.toDomainModel(): Task = Task(
        id = this.id,
        title = this.title,
        description = this.description,
        isCompleted = this.isCompleted
)

fun Task.toDataModel(): TaskEntity = TaskEntity(
        id = this.id,
        title = this.title,
        description = this.description,
        isCompleted = this.isCompleted
)

