package org.m0skit0.android.ikeachallenge.domain

import arrow.core.Option
import arrow.core.getOrElse
import arrow.core.toOption
import org.m0skit0.android.ikeachallenge.data.api.ProductDto
import org.m0skit0.android.ikeachallenge.data.api.ProductsDto
import kotlin.reflect.full.primaryConstructor

internal fun ProductsDto.toProductsByType(): List<Product> =
    products
        .map { list -> list.mapNotNull { it.toProductType() } }
        .getOrElse { emptyList() }

internal fun ProductDto.toProductType(): Product? =
    type.fold<Product?>({ null }) { type ->
        when (type) {
            TYPE_CHAIR -> Chair(id, imageUrl, info.flatMap { it.toChairInfo() }, name, price)
            TYPE_COUCH -> Couch(id, imageUrl, info.flatMap { it.toCouchInfo() }, name, price)
            else -> null
        }
    }

internal fun Map<String, String>.toChairInfo(): Option<ChairInfo> =
    ChairInfo::class.primaryConstructor?.parameters?.mapNotNull { it.name }?.run {
        ChairInfo(get(get(0)).toOption(), get(get(1)).toOption()).toOption()
    } ?: Option.empty()


internal fun Map<String, String>.toCouchInfo(): Option<CouchInfo> =
    CouchInfo::class.primaryConstructor?.parameters?.mapNotNull { it.name }?.run {
        CouchInfo(get(get(0)).toOption(), get(get(1)).toOption()).toOption()
    } ?: Option.empty()
