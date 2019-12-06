package org.m0skit0.android.ikeachallenge.log

import arrow.core.Option
import org.koin.core.context.GlobalContext

internal interface Logger {
    fun debug(message: String)
    fun info(message: String)
    fun warning(message: String)
    fun error(message: String, throwable: Option<Throwable> = Option.empty())
}

internal fun getLogger(): Logger = GlobalContext.get().koin.get()