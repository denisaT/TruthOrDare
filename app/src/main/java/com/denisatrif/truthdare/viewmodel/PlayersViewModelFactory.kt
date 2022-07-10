package com.denisatrif.truthdare.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.denisatrif.truthdare.db.AppDatabase
import com.denisatrif.truthdare.db.repos.PlayersRepository

class PlayersViewModelFactory(private val instance: AppDatabase) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PlayersViewModel(PlayersRepository(instance.playerDao())) as T
    }

}
