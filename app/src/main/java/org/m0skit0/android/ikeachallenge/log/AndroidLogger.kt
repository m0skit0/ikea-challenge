package org.m0skit0.android.ikeachallenge.log

import android.util.Log
import arrow.core.Option

internal class AndroidLogger : Logger {

    companion object  {
        private val TAG = AndroidLogger::class.java.simpleName
    }

    override fun debug(message: String) {
        Log.d(TAG, message)
    }

    override fun info(message: String) {
        Log.i(TAG, message)
    }

    override fun warning(message: String) {
        Log.w(TAG, message)
    }

    override fun error(message: String, throwable: Option<Throwable>) {
        throwable.fold({
            Log.e(TAG, message)
        }) {
            Log.e(TAG, message, it)
        }
    }
}