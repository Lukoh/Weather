package com.goforer.fitpettest.domain

import com.goforer.fitpettest.data.Params
import com.goforer.fitpettest.data.repository.Repository
import com.goforer.fitpettest.data.source.network.response.Resource
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Singleton
abstract class RepoUseCase(open val repository: Repository<Resource>) : UseCase<Resource>() {
    override fun run(viewModelScope: CoroutineScope, params: Params) =
        repository.handle(viewModelScope, params.query)

    fun invalidatePagingSource() = repository.invalidatePagingSource()
}