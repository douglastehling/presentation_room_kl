package com.douglas.room

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class WordViewModel(private val wordRepository: WordRepository, private val userRepository: UserRepository) : ViewModel() {

    val allWords: LiveData<List<Word>> = wordRepository.allWords.asLiveData()

    val allUsers: LiveData<List<User>> = userRepository.allUsers.asLiveData()

    fun insertWord(word: Word) = viewModelScope.launch {
        wordRepository.insert(word)
    }

    fun insertUser(user: User) = viewModelScope.launch {
        userRepository.insert(user)
    }
}

class WordViewModelFactory(private val wordRepository: WordRepository, private val userRepository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WordViewModel(wordRepository, userRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


