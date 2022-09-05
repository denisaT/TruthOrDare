package com.denisatrif.truthdare.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.denisatrif.truthdare.db.model.Player
import com.denisatrif.truthdare.db.repos.PlayersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlayersViewModel(
    private val playersRepository: PlayersRepository,
) : ViewModel() {

    var players = mutableListOf<Player>()

    fun addPlayers() {
        viewModelScope.launch(Dispatchers.IO) {
            playersRepository.insertAll(players)
            players.clear()
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

    fun startGame() {
        viewModelScope.launch(Dispatchers.IO) {
            addPlayers()
        }
    }

    private fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            playersRepository.deleteAll()
        }
    }

    fun deletePlayer(player: Player) {
        viewModelScope.launch(Dispatchers.IO) {
            playersRepository.delete(player)
        }
    }
}