package org.m0skit0.android.ikeachallenge.domain

import arrow.fx.IO

internal interface CartRepository {
    fun add(product: Product): IO<Unit>
    fun remove(product: Product): IO<Unit>
    fun total(): IO<Double>
}
