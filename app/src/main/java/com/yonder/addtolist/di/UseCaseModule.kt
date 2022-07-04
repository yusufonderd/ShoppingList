package com.yonder.addtolist.di

import com.yonder.addtolist.core.network.thread.CoroutineThread
import com.yonder.addtolist.data.repository.CategoryListRepository
import com.yonder.addtolist.data.repository.ProductRepository
import com.yonder.addtolist.data.repository.UserListRepository
import com.yonder.addtolist.domain.usecase.*
import com.yonder.addtolist.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/**
 * @author yusuf.onder
 * Created on 12.01.2022
 */

@[Module InstallIn(ViewModelComponent::class)]
object UseCaseModule {

    @[Provides]
    fun provideCreateListUseCase(
        userListRepository: UserListRepository,
        coroutineThread: CoroutineThread
    ): CreateList {
        return CreateList(userListRepository, coroutineThread)
    }

    @[Provides]
    fun provideDeleteUserListProduct(
        productRepository: ProductRepository
    ): DeleteProductOfUserList {
        return DeleteProductOfUserList(productRepository)
    }

    @[Provides]
    fun provideUpdateUserListUseCase(
        appDatabase: AppDatabase
    ): UpdateProductOfUserList {
        return UpdateProductOfUserList(appDatabase)
    }

    @[Provides]
    fun provideGetProductsWithCategoryUseCase(
        repository: CategoryListRepository,
        coroutineThread: CoroutineThread
    ): GetProductsWithCategory {
        return GetProductsWithCategory(repository, coroutineThread)
    }


}
