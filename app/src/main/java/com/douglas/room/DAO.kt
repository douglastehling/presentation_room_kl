package com.douglas.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {

    @Query("SELECT * FROM ${Constants.ROOM.WORD_TABLE_NAME} ORDER BY word ASC")
    fun getAlphabetizedWords(): Flow<List<Word>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Word)

    @Query("SELECT * FROM ${Constants.ROOM.WORD_TABLE_NAME} WHERE word LIKE :qStr LIMIT 1")
    fun findByWord(qStr: String): Word

    @Query("DELETE FROM ${Constants.ROOM.WORD_TABLE_NAME}")
    suspend fun deleteAll()
}

@Dao
interface UserDao {

    @Query("SELECT * FROM ${Constants.ROOM.USER_TABLE_NAME}") fun getAllUsers(): Flow<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE) fun insertUsers(users: User)

    @Update fun updateUser(user: User)

    @Delete fun deleteUser(user: User)

    @Query("DELETE FROM ${Constants.ROOM.USER_TABLE_NAME}")
    suspend fun deleteAll()
}