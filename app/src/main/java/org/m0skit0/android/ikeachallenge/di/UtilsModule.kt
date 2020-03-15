package org.m0skit0.android.ikeachallenge.di

import org.koin.core.module.Module
import org.koin.dsl.module
import org.m0skit0.android.ikeachallenge.log.AndroidLogger
import org.m0skit0.android.ikeachallenge.log.Logger

internal object UtilsModule : KoinModuleProvider {
    override fun module(): Module = module {
        single<Logger> { AndroidLogger() }
    }
}
