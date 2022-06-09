package com.deliveryhero.graphqlplayground.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface LaunchRepository {
    fun fetchLaunches(): Flow<PagingData<LaunchItem>>
}