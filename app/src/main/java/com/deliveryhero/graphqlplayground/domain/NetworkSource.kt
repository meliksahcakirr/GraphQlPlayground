package com.deliveryhero.graphqlplayground.domain

import com.deliveryhero.graphqlplayground.domain.LaunchData
import com.deliveryhero.graphqlplayground.domain.Result
import kotlinx.coroutines.flow.Flow

interface NetworkSource {

    fun queryLaunchList(pageSize: Int = 10, nextKey: String? = ""): Flow<Result<LaunchData>>
}