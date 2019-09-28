package com.san4o.just4fun.domain.model

data class GetTaskParams(
        val forceUpdate: Boolean,
        val filterType: TasksFilterType
) {
}