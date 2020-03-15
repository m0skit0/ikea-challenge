package org.m0skit0.android.ikeachallenge.domain

import arrow.fx.IO

internal interface ProductRepository {
    fun getProducts(): IO<List<Product>>
}
