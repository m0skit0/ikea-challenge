package org.m0skit0.android.ikeachallenge.util

import android.content.Context
import androidx.annotation.StringRes
import org.koin.core.context.GlobalContext
import org.m0skit0.android.ikeachallenge.di.koin

internal fun getStringResource(@StringRes id: Int) = koin().get<Context>().getString(id)
