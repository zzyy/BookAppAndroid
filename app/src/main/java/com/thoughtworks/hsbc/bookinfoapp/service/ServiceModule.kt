package com.thoughtworks.hsbc.bookinfoapp.service

import com.thoughtworks.hsbc.bookinfoapp.service.remote.BookRemoteApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
abstract class ServiceModule {

    @Binds
    abstract fun provideBookService(bookServiceImpl: BookServiceImpl): BookService
}