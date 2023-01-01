package com.goforer.weather.presentation.stateholders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goforer.weather.data.Params
import com.goforer.weather.data.source.network.response.Resource
import com.goforer.weather.data.source.network.response.Status
import com.goforer.weather.domain.RepoUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

open class MediatorSharedViewModel(private val useCase: RepoUseCase, params: Params) : ViewModel() {
    /*
   val value = useCase.run(viewModelScope, params).flatMapLatest {
        flow {
            emit(it)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = Resource().loading(LOADING)
    )

     */

    // You can implement code blow:
    // Just please visit below link if you'd like to know [StatFlow & SharedFlow]
    // Link : https://developer.android.com/kotlin/flow/stateflow-and-sharedflow

    fun invalidatePagingSource() = useCase.invalidatePagingSource()

    private val _value = MutableSharedFlow<Resource>()
    val value = _value

    init {
        viewModelScope.launch {
            value.emit(Resource().loading(Status.LOADING))
            useCase.run(viewModelScope, params).collectLatest {
                value.emit(it)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()

        Resource().loading(Status.LOADING)
    }
}