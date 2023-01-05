package com.denisatrif.truthdare.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.denisatrif.truthdare.db.model.Player
import com.denisatrif.truthdare.db.repos.PlayersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayersViewModel @Inject constructor(private val playersRepository: PlayersRepository) :
    ViewModel() {

    private val _playersList = mutableStateListOf<Player>()

    val playersList: List<Player>
        get() = _playersList

    fun addPlayer(item: Player) {
        _playersList.add(item)
    }

    fun removePlayer(item: Player) {
        _playersList.remove(item)
    }

    fun getPlayersCount() =
        _playersList.size

    private fun addAllPlayers() {
        viewModelScope.launch(Dispatchers.IO) {
            playersRepository.insertAll(_playersList)
            _playersList.clear()
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
        addAllPlayers()
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
