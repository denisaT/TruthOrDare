package com.denisatrif.truthdare.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.denisatrif.truthdare.db.AppDatabase
import com.denisatrif.truthdare.db.repos.PlayersRepositoryImpl

class PlayersViewModelFactory(private val instance: AppDatabase) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PlayersViewModel(PlayersRepositoryImpl(instance.playerDao())) as T
    }

}
