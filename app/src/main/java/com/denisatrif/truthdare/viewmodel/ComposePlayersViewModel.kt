package com.denisatrif.truthdare.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.denisatrif.truthdare.db.model.Player

class ComposePlayersViewModel : ViewModel() {

    private val _playersList = mutableStateListOf<Player>()

    val playersList: List<Player>
        get() = _playersList

    fun addPlayer(item: Player) {
        _playersList.add(item)
    }

    fun removePlayer(item: Player) {
        _playersList.remove(item)
    }
}