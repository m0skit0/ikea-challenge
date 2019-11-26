package org.m0skit0.android.ikeachallenge.data.api

import arrow.core.Option
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import java.lang.reflect.ParameterizedType

/**
 * Gson type adapter factory for Arrow's Option
 */
internal class OptionTypeAdapterFactory : TypeAdapterFactory {
    override fun <T : Any?> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
        val rawType = type.rawType as Class<*>
        if (rawType != Option::class.java) {
            return null
        }

        val parametrizedType = type.type as ParameterizedType
        val actualType = parametrizedType.actualTypeArguments[0]
        val adapter = gson.getAdapter(TypeToken.get(actualType))

        @Suppress("UNCHECKED_CAST")
        return OptionTypeAdapter(adapter) as TypeAdapter<T>
    }
}