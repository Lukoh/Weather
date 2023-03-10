package com.goforer.weather.data.repository

import com.goforer.weather.data.Query
import com.goforer.weather.data.source.network.NetworkErrorHandler
import com.goforer.weather.data.source.network.api.RestAPI
import com.goforer.base.storage.Storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
abstract class Repository<Resource> {
    @Inject
    lateinit var restAPI: RestAPI

    @Inject
    lateinit var networkErrorHandler: NetworkErrorHandler

    @Inject
    lateinit var storage: Storage

    companion object {
        internal const val ITEM_COUNT = 6

        internal const val API_KEY = "dec145755ed77d4e089fe5e284eca7c7"
    }

    abstract fun handle(viewModelScope: CoroutineScope, query: Query): SharedFlow<Resource>

    open fun invalidatePagingSource() {}

    protected fun handleNetworkError(errorMessage: String) {
        networkErrorHandler.handleError(errorMessage)
    }
}