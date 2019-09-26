package com.san4o.just4fun.domain.task

import com.san4o.just4fun.domain.model.TasksFilterType

data class GetTaskParams(
        val forceUpdate: Boolean,
        val filterType: TasksFilterType
) {
}