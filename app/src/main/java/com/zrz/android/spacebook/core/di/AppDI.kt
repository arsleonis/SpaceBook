package com.zrz.android.spacebook.core.di

import android.content.Context
import com.zrz.android.spacebook.core.observer.Observable
import com.zrz.android.spacebook.core.observer.Observer
import com.zrz.android.spacebook.entity.SBMessage
import com.zrz.android.spacebook.feature.chat.ChatViewModel
import com.zrz.android.spacebook.model.firebase.NetworkManager
import com.zrz.android.spacebook.model.firebase.NetworkManagerImpl
import com.zrz.android.spacebook.repository.message.MessageRepository
import com.zrz.android.spacebook.repository.message.MessageRepositoryImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

object AppDI {
    private val managerModule by lazy {
        module { single<NetworkManager> { NetworkManagerImpl() } }
    }
    private val observableModule by lazy {
        module { single<Observable<SBMessage, Observer<SBMessage>>> { NetworkManagerImpl() } }
    }
    private val repositoryModule by lazy {
        module { factory<MessageRepository> { MessageRepositoryImpl(get()) } }
    }
    private val viewModelModule by lazy {
        module { viewModel { ChatViewModel(androidApplication(), get(), get()) } }
    }

    fun setup(appContext: Context) {
        startKoin {
            androidContext(appContext)
            modules(
                listOf(
                    managerModule,
                    observableModule,
                    repositoryModule,
                    viewModelModule
                )
            )
        }
    }
}