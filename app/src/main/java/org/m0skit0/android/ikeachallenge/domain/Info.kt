package org.m0skit0.android.ikeachallenge.domain

import arrow.core.Option

internal sealed class Info(val color: Option<String>)
internal class ChairInfo(val material: Option<String>, color: Option<String>): Info(color)
internal class CouchInfo(val numberOfSeats: Option<String>, color: Option<String>): Info(color)