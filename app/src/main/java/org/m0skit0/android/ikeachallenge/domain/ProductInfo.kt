package org.m0skit0.android.ikeachallenge.domain

import arrow.core.Option

internal sealed class ProductInfo(val color: Option<String>)
internal class ChairInfo(val material: Option<String>, color: Option<String>): ProductInfo(color)
internal class CouchInfo(val numberOfSeats: Option<String>, color: Option<String>): ProductInfo(color)