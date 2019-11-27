package org.m0skit0.android.ikeachallenge.domain

import arrow.core.Option
import org.m0skit0.android.ikeachallenge.data.api.PriceDto

internal const val TYPE_CHAIR = "chair"
internal const val TYPE_COUCH = "couch"

internal sealed class Product(
    val id: Option<String> = Option.empty(),
    val imageUrl: Option<String> = Option.empty(),
    val info: Option<Info> = Option.empty(),
    val name: Option<String> = Option.empty(),
    val price: Option<PriceDto> = Option.empty()
)

internal class Chair(
    id: Option<String>,
    imageUrl: Option<String> = Option.empty(),
    info: Option<ChairInfo> = Option.empty(),
    name: Option<String> = Option.empty(),
    price: Option<PriceDto> = Option.empty()
): Product(id, imageUrl, info, name, price)


internal class Couch(
    id: Option<String>,
    imageUrl: Option<String> = Option.empty(),
    info: Option<CouchInfo> = Option.empty(),
    name: Option<String> = Option.empty(),
    price: Option<PriceDto> = Option.empty()
): Product(id, imageUrl, info, name, price)

internal sealed class Info(val color: Option<String>)
internal class ChairInfo(val material: Option<String>, color: Option<String>): Info(color)
internal class CouchInfo(val numberOfSeats: Option<String>, color: Option<String>): Info(color)