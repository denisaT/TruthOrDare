package com.denisatrif.truthdare.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.denisatrif.truthdare.R
import com.denisatrif.truthdare.db.model.Player
import com.denisatrif.truthdare.db.repos.PlayersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
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
            playersRepository.getCount().collect {
                item.order = it
                playersRepository.addPlayer(item)
            }
        }
    }

    fun getAllPlayers(): Flow<List<Player>> {
        return playersRepository.getAllPlayers()
    }

    fun deletePlayer(player: Player) {
        _playersList.remove(player)
        viewModelScope.launch(Dispatchers.IO) {
            playersRepository.delete(player)
        }
    }

    fun getAllIds(): Flow<List<Int>> {
        return playersRepository.getListOfIds()
    }

    fun getNext(id: Int): Flow<Player> {
        return playersRepository.getPlayerWithId(id)
    }

    private fun getCount(): Flow<Int> {
        return playersRepository.getCount()
    }

    fun saveNumberOfPlayers() {
        viewModelScope.launch(Dispatchers.IO) {
            getCount().collect { count ->
                val prefs = app.getSharedPreferences(
                    app.getString(R.string.preference_file_key), Application.MODE_PRIVATE
                )
                prefs?.edit()?.putInt("playersCount", count)?.apply()
            }
        }
    }
}
