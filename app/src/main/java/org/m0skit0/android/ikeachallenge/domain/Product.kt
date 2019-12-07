package org.m0skit0.android.ikeachallenge.domain

import arrow.core.Option
import org.m0skit0.android.ikeachallenge.data.api.PriceDto

internal sealed class Product(
    val id: Option<String> = Option.empty(),
    val imageUrl: Option<String> = Option.empty(),
    val info: Option<ProductInfo> = Option.empty(),
    val name: Option<String> = Option.empty(),
    val price: Option<PriceDto> = Option.empty()
)

internal const val TYPE_CHAIR = "chair"

internal class Chair(
    id: Option<String>,
    imageUrl: Option<String> = Option.empty(),
    info: Option<ChairInfo> = Option.empty(),
    name: Option<String> = Option.empty(),
    price: Option<PriceDto> = Option.empty()
): Product(id, imageUrl, info, name, price)

internal const val TYPE_COUCH = "couch"

internal class Couch(
    id: Option<String>,
    imageUrl: Option<String> = Option.empty(),
    info: Option<CouchInfo> = Option.empty(),
    name: Option<String> = Option.empty(),
    price: Option<PriceDto> = Option.empty()
): Product(id, imageUrl, info, name, price)
