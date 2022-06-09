package com.deliveryhero.graphqlplayground.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.deliveryhero.graphqlplayground.LaunchListQuery
import com.deliveryhero.graphqlplayground.domain.LaunchItem

class LaunchPagingSource(private val apolloClient: ApolloClient) :
    PagingSource<String, LaunchItem>() {

    private var prevKey: String? = null

    override suspend fun load(params: LoadParams<String>): LoadResult<String, LaunchItem> {
        return try {
            val key = params.key
            val response = apolloClient.query(
                LaunchListQuery(
                    Optional.presentIfNotNull(key),
                    Optional.Present(params.loadSize)
                )
            ).execute().data
            val launches = response?.launches?.launches ?: emptyList()
            val hasMore = response?.launches?.hasMore
            val cursor = response?.launches?.cursor
            prevKey = key
            LoadResult.Page(
                data = launches.map {
                    LaunchItem(
                        it!!.id,
                        it.site,
                        it.isBooked,
                        it.mission?.name,
                        it.mission?.missionPatch
                    )
                },
                prevKey = prevKey,
                nextKey = if (hasMore == true) cursor else null
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }


    override fun getRefreshKey(state: PagingState<String, LaunchItem>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
                ?: state.closestPageToPosition(anchorPosition)?.nextKey
        }
    }
}