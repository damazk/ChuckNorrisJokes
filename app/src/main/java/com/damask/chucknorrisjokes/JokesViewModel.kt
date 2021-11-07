package com.damask.chucknorrisjokes

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class JokesViewModel(state: SavedStateHandle) : ViewModel(){

    companion object {
        private val JOKES_KEY = "jokes"
    }

    private val savedStateHandle = state

    fun saveJokesJsonObject(json: String) {
        savedStateHandle.set(JOKES_KEY, json)
    }

    fun getRecentlyJokes() = savedStateHandle.get(JOKES_KEY) ?: ""

}