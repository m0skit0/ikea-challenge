package org.m0skit0.android.ikeachallenge.log

import arrow.core.Option

internal interface Logger {
    fun debug(message: String)
    fun info(message: String)
    fun warning(message: String)
    fun error(message: String, throwable: Option<Throwable> = Option.empty())
}