package com.denisatrif.truthdare.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.denisatrif.truthdare.db.AppDatabase
import com.denisatrif.truthdare.db.repos.PlayersRepositoryImpl
import com.denisatrif.truthdare.db.repos.TruthDareRepositoryImpl

class GameViewModelFactory(private val appDatabase: AppDatabase) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GameViewModel(
            TruthDareRepositoryImpl(appDatabase.truthDareDao()),
            PlayersRepositoryImpl(appDatabase.playerDao())
        ) as T
    }
}
