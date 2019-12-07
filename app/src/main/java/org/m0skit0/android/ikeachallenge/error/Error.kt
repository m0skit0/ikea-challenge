package org.m0skit0.android.ikeachallenge.error

import arrow.core.Option

internal class APIException(message: String, cause: Option<Throwable> = Option.empty()): Exception(message, cause.orNull())
internal class ProductNotFound(message: String, cause: Option<Throwable> = Option.empty()): Exception(message, cause.orNull())
internal class InvalidProduct(message: String, cause: Option<Throwable> = Option.empty()): Exception(message, cause.orNull())