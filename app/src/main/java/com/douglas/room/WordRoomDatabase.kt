package com.douglas.room

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.OffsetDateTime

@Database(
    entities = [Word::class, User::class],
    version = 3,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 2, to = 3)
    ]
)
@TypeConverters(Converters::class)
abstract class WordRoomDatabase : RoomDatabase() {

    abstract fun wordDao(): WordDao
    abstract fun userDao(): UserDao

    private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        @RequiresApi(Build.VERSION_CODES.O)
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    // Seed
                    var wordDao = database.wordDao()
                    wordDao.deleteAll()
                    var word = Word("Hello")
                    wordDao.insert(word)

                    // Seed
                    var userDao = database.userDao()
                    userDao.deleteAll()
                    val user = User(
                        "02",
                        "Gabriel",
                        OffsetDateTime.parse("2017-10-17T07:23:19.120+00:00"),
                        22,
                        Phone(55, "0000-0000")
                    )
                    userDao.insertUsers(user)
                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: WordRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): WordRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, WordRoomDatabase::class.java, Constants.ROOM.DB_NAME)
                    .addCallback(WordDatabaseCallback(scope))
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_1)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}