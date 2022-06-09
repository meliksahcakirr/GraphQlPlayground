package com.deliveryhero.graphqlplayground.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

class FetchLaunchesPagingUseCaseImpl(private val repository: LaunchRepository) :
    FetchLaunchesPagingUseCase {

    override fun fetchLaunches(): Flow<PagingData<LaunchItem>> {
        return repository.fetchLaunches()
    }

}