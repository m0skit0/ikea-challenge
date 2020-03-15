package org.m0skit0.android.ikeachallenge.data.repository

import arrow.fx.IO
import org.m0skit0.android.ikeachallenge.domain.CartRepository
import org.m0skit0.android.ikeachallenge.domain.Product

internal class CartRepositoryImpl : CartRepository {

    private var cartHolder: Map<Product, Int> = mapOf()

    override fun add(product: Product): IO<Unit> = IO {
        product.quantity().let { quantity ->
            cartHolder = cartHolder + mapOf(product to quantity.inc())
        }
    }

    override fun remove(product: Product): IO<Unit> = IO {
        product.quantity().let { quantity ->
            if (quantity > 0) {
                cartHolder = cartHolder + mapOf(product to quantity.dec())
            }
        }
    }

    override fun total(): IO<Double> = IO {
        cartHolder
            .map { it.total() }
            .fold(0.0, { acc, partialTotal -> acc + partialTotal })
    }

    private fun Map.Entry<Product, Int>.total(): Double = run {
        val (product, quantity) = this
        product.price.orNull()?.value?.orNull() ?: 0.0 * quantity
    }

    private fun Product.quantity(): Int = cartHolder.getOrElse(this, { 0 })
}
