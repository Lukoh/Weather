package com.goforer.weather.domain

import com.goforer.weather.data.Params
import com.goforer.weather.data.repository.Repository
import com.goforer.weather.data.source.network.response.Resource
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Singleton
abstract class RepoUseCase(open val repository: Repository<Resource>) : UseCase<Resource>() {
    override fun run(viewModelScope: CoroutineScope, params: Params) =
        repository.handle(viewModelScope, params.query)

    fun invalidatePagingSource() = repository.invalidatePagingSource()
}