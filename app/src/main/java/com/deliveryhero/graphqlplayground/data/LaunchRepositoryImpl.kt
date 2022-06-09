package com.deliveryhero.graphqlplayground.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.apollographql.apollo3.ApolloClient
import com.deliveryhero.graphqlplayground.domain.LaunchItem
import com.deliveryhero.graphqlplayground.domain.LaunchRepository
import kotlinx.coroutines.flow.Flow

private const val PAGE_SIZE = 10

class LaunchRepositoryImpl(private val apolloClient: ApolloClient) : LaunchRepository {

    override fun fetchLaunches(): Flow<PagingData<LaunchItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { LaunchPagingSource(apolloClient) },
            initialKey = null
        ).flow
    }

}