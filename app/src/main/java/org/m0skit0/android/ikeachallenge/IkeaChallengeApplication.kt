package org.m0skit0.android.ikeachallenge

import android.app.Application
import org.m0skit0.android.ikeachallenge.di.initializeKoin

internal class IkeaChallengeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initializeKoin()
    }
}