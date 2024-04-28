package com.thoughtworks.hsbc.bookinfoapp.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class GsonUtil {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(LocalDate::class.java, LocalDateTypeAdapter())
            .create()
    }
}

private class LocalDateTypeAdapter : TypeAdapter<LocalDate?>() {
    private val formatter = DateTimeFormatter.ISO_DATE

    override fun write(out: JsonWriter, value: LocalDate?) {
        if (value == null) {
            out.nullValue()
        } else {
            out.value(formatter.format(value))
        }
    }

    override fun read(input: JsonReader): LocalDate? {
        if (input.peek() == null) {
            return null
        } else if (input.peek() == JsonToken.NULL) {
            input.nextNull()
            return null
        }

        return try {
            LocalDate.parse(input.nextString(), formatter)
        } catch (_: Exception) {
            null
        }
    }
}