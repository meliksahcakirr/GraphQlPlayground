package com.deliveryhero.graphqlplayground.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FetchLaunchesUseCaseImpl @Inject constructor(
    private val networkSource: NetworkSource,
    private val dispatcher: CoroutineDispatcher
) : FetchLaunchesUseCase {

    override fun fetchLaunches(): Flow<Result<LaunchData>> {
        return networkSource.queryLaunchList()
            .flowOn(dispatcher)
    }
}