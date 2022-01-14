package com.denisatrif.truthdare.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.denisatrif.truthdare.db.model.Player
import com.denisatrif.truthdare.db.repos.PlayersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlayersViewModel(
    private val playersRepository: PlayersRepository,
) : ViewModel() {

    fun addPlayer(player: Player) {
        viewModelScope.launch(Dispatchers.IO) {
            if (!playersRepository.exists(player.name.toString()))
                playersRepository.addPlayer(player)
        }
    }

    fun addPlayers(players: List<Player>) {
        viewModelScope.launch(Dispatchers.IO) {
            playersRepository.insertAll(players)
        }
    }

    fun getAllPlayers() {
        playersRepository.getAllPlayers()
    }

    companion object {
    }
}