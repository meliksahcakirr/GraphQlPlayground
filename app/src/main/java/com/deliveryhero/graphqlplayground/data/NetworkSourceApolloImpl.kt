package com.deliveryhero.graphqlplayground.data

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.deliveryhero.graphqlplayground.LaunchListQuery
import com.deliveryhero.graphqlplayground.domain.LaunchData
import com.deliveryhero.graphqlplayground.domain.LaunchItem
import com.deliveryhero.graphqlplayground.domain.NetworkSource
import com.deliveryhero.graphqlplayground.domain.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NetworkSourceApolloImpl @Inject constructor(
    private val client: ApolloClient,
    private val dispatcher: CoroutineDispatcher
) : NetworkSource {

    override fun queryLaunchList(pageSize: Int, nextKey: String?): Flow<Result<LaunchData>> {
        return flow {
            client.query(LaunchListQuery(Optional.presentIfNotNull(nextKey), Optional.Present(pageSize)))
                .execute().data?.let { data ->
                val result = Result.Success(
                    LaunchData(
                        data.launches.cursor,
                        data.launches.hasMore,
                        data.launches.launches.map {
                            LaunchItem(
                                it!!.id,
                                it.site,
                                it.isBooked,
                                it.mission?.name,
                                it.mission?.missionPatch
                            )
                        }
                    )
                )
                emit(result)
            } ?: emit(
                Result.Error(
                    Exception("Launch list is not available")
                )
            )
        }.catch {
            emit(Result.Error(it))
        }.flowOn(dispatcher)
    }

}