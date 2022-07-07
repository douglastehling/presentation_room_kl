package com.douglas.room

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class WordsApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { WordRoomDatabase.getDatabase(this, applicationScope) }
    val wordrepository by lazy {
        WordRepository(database.wordDao());
    }
    val userrepository by lazy {
        UserRepository(database.userDao());
    }
}