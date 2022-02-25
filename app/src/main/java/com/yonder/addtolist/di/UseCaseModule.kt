package com.yonder.addtolist.di

import com.yonder.addtolist.core.network.thread.CoroutineThread
import com.yonder.addtolist.data.repository.LoginRepository
import com.yonder.addtolist.local.AppDatabase
import com.yonder.addtolist.network.ApiService
import com.yonder.addtolist.data.repository.UserListRepository
import com.yonder.addtolist.data.repository.ProductRepository
import com.yonder.addtolist.domain.mapper.LoginMapper
import com.yonder.addtolist.domain.usecase.GetProductsWithCategory
import com.yonder.addtolist.domain.usecase.CreateList
import com.yonder.addtolist.domain.usecase.DeleteProductOfUserList
import com.yonder.addtolist.domain.usecase.FacebookGraphUseCase
import com.yonder.addtolist.domain.usecase.LoginUseCase
import com.yonder.addtolist.domain.usecase.UpdateProductOfUserList
import com.yonder.addtolist.data.repository.CategoryListRepository
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
    fun provideLoginUseCase(
        loginRepository: LoginRepository,
        loginMapper: LoginMapper,
        coroutineThread: CoroutineThread
    ): LoginUseCase {
        return LoginUseCase(loginRepository, loginMapper, coroutineThread)
    }

    @[Provides]
    fun provideUpdateUserListUseCase(
        appDatabase: AppDatabase,
        api: ApiService
    ): UpdateProductOfUserList {
        return UpdateProductOfUserList(appDatabase, api)
    }

    @[Provides]
    fun provideGetProductsWithCategoryUseCase(
        repository: CategoryListRepository,
        coroutineThread: CoroutineThread
    ): GetProductsWithCategory {
        return GetProductsWithCategory(repository, coroutineThread)
    }

    @[Provides]
    fun provideFacebookGraphUseCase(): FacebookGraphUseCase {
        return FacebookGraphUseCase()
    }

}
