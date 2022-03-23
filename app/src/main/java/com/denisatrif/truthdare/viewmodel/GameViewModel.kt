package com.denisatrif.truthdare.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.denisatrif.truthdare.db.model.Player
import com.denisatrif.truthdare.db.model.QuestionType
import com.denisatrif.truthdare.db.model.TruthDare
import com.denisatrif.truthdare.db.repos.PlayersRepository
import com.denisatrif.truthdare.db.repos.TruthDareRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameViewModel(
    private val truthDaresRepository: TruthDareRepository,
    private val playersRepository: PlayersRepository
) : ViewModel() {

    var players = mutableListOf<Player>()
    var currentPlayerPosition = 0
    var dirtyModeCounter = 0
    var sexyModeCounter = 0
    var partyModeCounter = 0

    private fun nextPlayer() {
        if (currentPlayerPosition < players.size - 1) {
            currentPlayerPosition++
        } else {
            currentPlayerPosition = 0
        }
    }

    fun getAllPlayers(): LiveData<List<Player>> {
        val liveData = MutableLiveData<List<Player>>()
        viewModelScope.launch(Dispatchers.IO) {
            val players = playersRepository.getAllPlayers()
            liveData.postValue(players)
        }
        return liveData
    }

    fun getCurrentPlayer() = players[currentPlayerPosition]

    fun getRandomTruth(type: QuestionType): LiveData<TruthDare> {
        val liveData = MutableLiveData<TruthDare>()
        viewModelScope.launch(Dispatchers.IO) {
            val randomTruth = truthDaresRepository.getRandomTruth(type)
            liveData.postValue(randomTruth)
        }
        return liveData
    }

    fun getRandomDare(type: QuestionType): LiveData<TruthDare> {
        val liveData = MutableLiveData<TruthDare>()
        viewModelScope.launch(Dispatchers.IO) {
            val randomDare = truthDaresRepository.getRandomDare(type)
            liveData.postValue(randomDare)
        }
        return liveData
    }

    fun getNextPlayer(): Player {
        nextPlayer()
        return players[currentPlayerPosition]
    }

    fun incrementCounter(questionType: QuestionType) {
        when (questionType) {
            QuestionType.DIRTY -> dirtyModeCounter++
            QuestionType.PARTY -> partyModeCounter++
            QuestionType.SEXY -> sexyModeCounter++
        }

    }

    fun isLimitReachedForType(questionType: QuestionType): Boolean {
        return when (questionType) {
            QuestionType.DIRTY -> dirtyModeCounter >= 5
            QuestionType.PARTY -> partyModeCounter >= 5
            QuestionType.SEXY -> sexyModeCounter >= 5
        }
    }
}
