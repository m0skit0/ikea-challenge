package org.m0skit0.android.ikeachallenge.util

import android.content.Context
import androidx.annotation.StringRes
import org.koin.core.context.GlobalContext

internal fun koin() = GlobalContext.get().koin
internal fun getStringResource(@StringRes id: Int) = koin().get<Context>().getString(id)
