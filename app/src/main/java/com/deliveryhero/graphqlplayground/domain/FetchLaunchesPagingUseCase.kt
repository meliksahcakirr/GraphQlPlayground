package com.deliveryhero.graphqlplayground.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface FetchLaunchesPagingUseCase {

    fun fetchLaunches(): Flow<PagingData<LaunchItem>>
}