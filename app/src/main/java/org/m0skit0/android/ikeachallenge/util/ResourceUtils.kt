package org.m0skit0.android.ikeachallenge.util

import android.content.Context
import org.m0skit0.android.ikeachallenge.di.koin
import java.io.InputStream

internal fun String.asAsset(): InputStream = koin().get<Context>().assets.open(this)
