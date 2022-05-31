package com.deliveryhero.graphqlplayground.domain

import kotlinx.coroutines.flow.Flow

interface FetchLaunchesUseCase {

    fun fetchLaunches(): Flow<Result<LaunchData>>
}