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
    private var current: Int = 0

    fun addPlayer(item: Player) {
        _playersList.add(item)
        viewModelScope.launch(Dispatchers.IO) {
            playersRepository.addPlayer(item)
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

    fun deletePlayer(player: Player) {
        _playersList.remove(player)
        viewModelScope.launch(Dispatchers.IO) {
            playersRepository.delete(player)
        }
    }

    fun getNext(): LiveData<Player> {
        val liveData = MutableLiveData<Player>()
        viewModelScope.launch(Dispatchers.IO) {
            val player = playersRepository.getNext(current++)
            liveData.postValue(player)
        }
        return liveData
    }
}
