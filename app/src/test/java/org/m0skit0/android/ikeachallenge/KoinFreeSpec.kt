package org.m0skit0.android.ikeachallenge

import io.kotlintest.TestCase
import io.kotlintest.TestResult
import io.kotlintest.specs.FreeSpec
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.test.KoinTest

abstract class KoinFreeSpec : FreeSpec(), KoinTest {

    abstract val module: Module

    override fun beforeTest(testCase: TestCase) {
        startKoin {
            modules(module)
        }
    }

    override fun afterTest(testCase: TestCase, result: TestResult) {
        stopKoin()
    }
}