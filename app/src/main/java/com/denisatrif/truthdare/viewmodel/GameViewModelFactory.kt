package com.denisatrif.truthdare.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.denisatrif.truthdare.db.AppDatabase
import com.denisatrif.truthdare.db.repos.TruthDareRepository

class GameViewModelFactory(private val appDatabase: AppDatabase): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GameViewModel(TruthDareRepository(appDatabase.truthDareDao())) as T
    }
}
