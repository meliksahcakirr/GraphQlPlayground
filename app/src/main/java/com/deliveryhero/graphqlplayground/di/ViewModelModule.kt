package com.deliveryhero.graphqlplayground.di

import com.apollographql.apollo3.ApolloClient
import com.deliveryhero.graphqlplayground.data.LaunchRepositoryImpl
import com.deliveryhero.graphqlplayground.data.NetworkSourceApolloImpl
import com.deliveryhero.graphqlplayground.domain.DefaultDispatcher
import com.deliveryhero.graphqlplayground.domain.FetchLaunchesPagingUseCase
import com.deliveryhero.graphqlplayground.domain.FetchLaunchesPagingUseCaseImpl
import com.deliveryhero.graphqlplayground.domain.FetchLaunchesUseCase
import com.deliveryhero.graphqlplayground.domain.FetchLaunchesUseCaseImpl
import com.deliveryhero.graphqlplayground.domain.IoDispatcher
import com.deliveryhero.graphqlplayground.domain.LaunchRepository
import com.deliveryhero.graphqlplayground.domain.NetworkSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher

@InstallIn(ViewModelComponent::class)
@Module
object ViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideNetworkSource(
        apolloClient: ApolloClient,
        @IoDispatcher
        dispatcher: CoroutineDispatcher
    ): NetworkSource {
        return NetworkSourceApolloImpl(apolloClient, dispatcher)
    }

    @Provides
    @ViewModelScoped
    fun provideFetchLaunchesUseCase(
        networkSource: NetworkSource,
        @DefaultDispatcher
        dispatcher: CoroutineDispatcher
    ): FetchLaunchesUseCase {
        return FetchLaunchesUseCaseImpl(networkSource, dispatcher)
    }

    @Provides
    @ViewModelScoped
    fun provideRepository(apolloClient: ApolloClient): LaunchRepository {
        return LaunchRepositoryImpl(apolloClient)
    }

    @Provides
    @ViewModelScoped
    fun provideFetchLaunchesPagingUseCase(
        repository: LaunchRepository,
    ): FetchLaunchesPagingUseCase {
        return FetchLaunchesPagingUseCaseImpl(repository)
    }
}