package org.m0skit0.android.ikeachallenge.domain

import arrow.fx.IO

/**
 * Defines repository for products.
 */
internal interface ProductRepository {

    /**
     * Returns IO monad for list of products.
     */
    fun getProducts(): IO<List<Product>>

    /**
     * Returns IO monad for adding a product to the cart.
     */
    fun addProductToCart(id: String): IO<Unit>
}
