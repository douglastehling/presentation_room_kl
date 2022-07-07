package com.douglas.room

import android.os.Build
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class Converters {
    private val formatter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        DateTimeFormatter.ISO_OFFSET_DATE_TIME
    } else {
        TODO("VERSION.SDK_INT < O")
    }

    @TypeConverter
    fun toOffsetDateTime(value: String?): OffsetDateTime? {
        return value?.let {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                formatter.parse(value, OffsetDateTime::from)
            } else {
                TODO("VERSION.SDK_INT < O")
            }
        }
    }

    @TypeConverter
    fun fromOffsetDateTime(date: OffsetDateTime?): String? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            date?.format(formatter)
        } else {
            TODO("VERSION.SDK_INT < O")
        }
    }
}