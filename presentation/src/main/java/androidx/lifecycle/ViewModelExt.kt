package androidx.lifecycle

import kotlinx.coroutines.Job
import java.io.Closeable

fun Job.cancelOn(viewModel: ViewModel) {
    val job = this
    val tag = job.hashCode().toString()
    viewModel.setTagIfAbsent(tag, Closeable { job.cancel() })
}

fun ViewModel.addClosable(closeable: Closeable) {
    this.setTagIfAbsent(closeable.hashCode().toString(), closeable)
}

