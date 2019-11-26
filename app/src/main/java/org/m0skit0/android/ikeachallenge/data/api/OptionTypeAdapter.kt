package org.m0skit0.android.ikeachallenge.data.api

import arrow.core.Option
import arrow.core.toOption
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.io.IOException

/**
 * Gson type adapter for Arrow's Option
 */
internal class OptionTypeAdapter<T>(private val adapter: TypeAdapter<T>) : TypeAdapter<Option<T>>() {

    override fun write(out: JsonWriter, value: Option<T>) {
        value.fold({
            out.nullValue()
        }) {
            adapter.write(out, it)
        }
    }

    @Throws(IOException::class)
    override fun read(input: JsonReader): Option<T> =
        input.peek()?.run {
            adapter.read(input).toOption()
        } ?: run {
            input.nextNull()
            Option.empty<T>()
        }
}