package org.m0skit0.android.ikeachallenge.di

import android.app.Application
import android.content.Context
import androidx.annotation.StringRes
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin

internal fun koin() = GlobalContext.get().koin

private val modules = listOf(
    DataModule,
    RepositoryModule,
    UseCaseModule,
    UtilsModule,
    ViewModelModule
).map { it.module() }

internal fun Application.initializeKoin() {
    startKoin {
        androidContext(this@initializeKoin)
        modules(modules)
    }
}
