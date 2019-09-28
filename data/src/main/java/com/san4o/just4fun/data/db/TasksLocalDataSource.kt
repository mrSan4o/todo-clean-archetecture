/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.san4o.just4fun.data.source.local

import com.example.android.architecture.blueprints.todoapp.data.source.local.TasksDao
import com.san4o.just4fun.data.repositories.TasksDataSource
import com.san4o.just4fun.data.toDataModel
import com.san4o.just4fun.data.toDomainModel
import com.san4o.just4fun.domain.core.Error
import com.san4o.just4fun.domain.core.Result
import com.san4o.just4fun.domain.core.runCatching
import com.san4o.just4fun.domain.model.Task
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Concrete implementation of a data source as a db.
 */
class TasksLocalDataSource constructor(
        private val tasksDao: TasksDao,
        private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : TasksDataSource {

    override suspend fun getTasks(): Result<List<Task>> = withContext(ioDispatcher) {
        //        return@withContext try {
//            Success(tasksDao.getTasks().map { it.toDomainModel() })
//        } catch (e: Exception) {
//            Failure(e)
//        }

        return@withContext runCatching { tasksDao.getTasks().map { it.toDomainModel() } }
    }

    override suspend fun getTask(taskId: String): Result<Task> = withContext(ioDispatcher) {

        val task = tasksDao.getTaskById(taskId)
        if (task != null) {
            return@withContext Result.Success(task.toDomainModel())
        } else {
            return@withContext Result.Failure(Error.Message("Task not found!"))
        }

    }

    override suspend fun saveTask(task: Task) = withContext(ioDispatcher) {
        tasksDao.insertTask(task.toDataModel())
    }

    override suspend fun completeTask(task: Task) = withContext(ioDispatcher) {
        tasksDao.updateCompleted(task.id, true)
    }

    override suspend fun completeTask(taskId: String) {
        tasksDao.updateCompleted(taskId, true)
    }

    override suspend fun activateTask(task: Task) = withContext(ioDispatcher) {
        tasksDao.updateCompleted(task.id, false)
    }

    override suspend fun activateTask(taskId: String) {
        tasksDao.updateCompleted(taskId, false)
    }

    override suspend fun clearCompletedTasks() = withContext<Unit>(ioDispatcher) {
        tasksDao.deleteCompletedTasks()
    }

    override suspend fun deleteAllTasks() = withContext(ioDispatcher) {
        tasksDao.deleteTasks()
    }

    override suspend fun deleteTask(taskId: String) = withContext<Unit>(ioDispatcher) {
        tasksDao.deleteTaskById(taskId)
    }
}
