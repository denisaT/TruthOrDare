package com.denisatrif.truthdare.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.denisatrif.truthdare.R
import com.denisatrif.truthdare.db.model.Player
import com.denisatrif.truthdare.db.repos.PlayersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayersViewModel @Inject constructor(
    private val playersRepository: PlayersRepository,
    private val app: Application
) : ViewModel() {

    private val _playersList = mutableStateListOf<Player>()

    fun addPlayer(item: Player) {
        _playersList.add(item)
        viewModelScope.launch(Dispatchers.IO) {
            val count = playersRepository.getCount()
            item.order = count
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

    fun getNext(current: Int): LiveData<Player> {
        val liveData = MutableLiveData<Player>()
        viewModelScope.launch(Dispatchers.IO) {
            val count = playersRepository.getCount()
            val nextPlayerIndex = current % count
            print("$nextPlayerIndex DENISA")
            val player = playersRepository.getPlayerAt(nextPlayerIndex)
            liveData.postValue(player)
        }
        return liveData
    }

    private fun getCount(): LiveData<Int> {
        val liveData = MutableLiveData<Int>()
        viewModelScope.launch(Dispatchers.IO) {
            val count = playersRepository.getCount()
            liveData.postValue(count)
        }
        return liveData
    }

    fun saveNumberOfPlayers() {
        val count = getCount().value
        val prefs = app.getSharedPreferences(
            app.getString(R.string.preference_file_key), Application.MODE_PRIVATE
        )
        prefs?.edit()?.putInt("playersCount", count ?: 0)?.apply()
    }
}
