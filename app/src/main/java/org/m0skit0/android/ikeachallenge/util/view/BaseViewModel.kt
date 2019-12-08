package org.m0skit0.android.ikeachallenge.util.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import arrow.core.Either
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.m0skit0.android.ikeachallenge.di.NAMED_MUTABLE_BOOLEAN
import org.m0skit0.android.ikeachallenge.di.NAMED_MUTABLE_ERROR

internal abstract class BaseViewModel : ViewModel(), CoroutineScope by CoroutineScope(Dispatchers.IO), KoinComponent {

    protected val mutableIsLoading: MutableLiveData<Boolean> by inject(NAMED_MUTABLE_BOOLEAN)
    val isLoading: LiveData<Boolean> by lazy { mutableIsLoading }

    protected val mutableError: MutableLiveData<Throwable> by inject(NAMED_MUTABLE_ERROR)
    val error: LiveData<Throwable> by lazy { mutableError }

    protected fun <T> Either<Throwable, T>.getOrPostError(block: (T) -> Unit) {
        fold({
            mutableError.postValue(it)
        }) {
            block(it)
        }
    }

    protected fun launchWithLoading(block: suspend () -> Unit) {
        mutableIsLoading.postValue(true)
        launch {
            block()
            mutableIsLoading.postValue(false)
        }
    }

    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        cancel()
    }
}
