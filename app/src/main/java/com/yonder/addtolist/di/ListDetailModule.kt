package com.yonder.addtolist.di

import com.yonder.addtolist.core.network.thread.CoroutineThread
import com.yonder.addtolist.data.repository.CategoryListRepository
import com.yonder.addtolist.data.repository.CategoryListRepositoryImpl
import com.yonder.addtolist.scenes.listdetail.domain.category.ProductQueryUseCase
import com.yonder.addtolist.scenes.listdetail.domain.category.ProductQueryUseCaseImpl
import com.yonder.addtolist.domain.usecase.ProductUseCase
import com.yonder.addtolist.domain.usecase.ProductUseCaseImpl
import com.yonder.addtolist.data.datasource.CategoryDataSource
import com.yonder.addtolist.data.datasource.CategoryDataSourceImpl
import com.yonder.addtolist.data.local.UserPreferenceDataStore
import com.yonder.addtolist.domain.uimodel.ProductEntitySummaryToUiModel
import com.yonder.addtolist.local.AppDatabase
import com.yonder.addtolist.domain.usecase.AddProductUseCase
import com.yonder.addtolist.domain.usecase.AddProductUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * @author yusuf.onder
 * Created on 19.07.2021
 */

@[Module InstallIn(SingletonComponent::class)]
interface ListDetailModule {

    companion object {
        @[Provides]
        fun provideProductQueryUseCase(
            appDatabase: AppDatabase,
            userPreferenceDataStore: UserPreferenceDataStore,
            dispatcher: CoroutineThread,
            mapper: ProductEntitySummaryToUiModel
        ): ProductQueryUseCase {
            return ProductQueryUseCaseImpl(appDatabase, userPreferenceDataStore,dispatcher,mapper)
        }

    }


    @get:[Binds]
    val CategoryListRepositoryImpl.repository: CategoryListRepository

    @get:[Binds]
    val CategoryDataSourceImpl.dataSource: CategoryDataSource

    @get:[Binds]
    val ProductUseCaseImpl.productUseCase: ProductUseCase

    @get:[Binds]
    val AddProductUseCaseImpl.addProductUseCase: AddProductUseCase

}
