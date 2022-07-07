package com.douglas.room

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import org.jetbrains.annotations.NotNull
import java.time.OffsetDateTime
import java.util.*


@Entity(tableName = Constants.ROOM.WORD_TABLE_NAME)
data class Word(
    @PrimaryKey @ColumnInfo(name = "word") val word: String = "",
    @ColumnInfo(name = "enable") val isEnable: Boolean = true,
    val check: Boolean? = true
)

@Entity(tableName = Constants.ROOM.USER_TABLE_NAME)
data class User(
    @PrimaryKey val uuid: String,
    var name: String,
    var birthday: OffsetDateTime? = null,
    @ColumnInfo(name = "user_age") var age: Int,
    @Embedded(prefix = "user_") val phone: Phone
)
data class Phone(@ColumnInfo(name = "country_code") val countryCode: Int, val number: String)






// Migrations
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE ${Constants.ROOM.WORD_TABLE_NAME} ADD COLUMN enable INTEGER NOT NULL DEFAULT(0)")
    }
}

val MIGRATION_2_1: Migration = object : Migration(2, 1) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "CREATE TABLE tableAux (word TEXT NOT NULL, PRIMARY KEY(word))"
        )

        database.execSQL(
            "INSERT INTO tableAux (word) SELECT word FROM ${Constants.ROOM.WORD_TABLE_NAME}"
        )

        database.execSQL("DROP TABLE ${Constants.ROOM.WORD_TABLE_NAME}")
        database.execSQL("ALTER TABLE tableAux RENAME TO ${Constants.ROOM.WORD_TABLE_NAME}")
    }
}