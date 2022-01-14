package com.denisatrif.truthdare.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.denisatrif.truthdare.db.model.TruthDare
import com.denisatrif.truthdare.db.repos.TruthDareRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameViewModel(private val truthDaresRepository: TruthDareRepository) : ViewModel() {

    fun getRandomTruth(): LiveData<TruthDare> {
        val liveData = MutableLiveData<TruthDare>()
        viewModelScope.launch(Dispatchers.IO) {
            val randomTruth = truthDaresRepository.getRandomTruth()
            liveData.postValue(randomTruth)
        }
        return liveData
    }

    fun getRandomDare(): LiveData<TruthDare> {
        val liveData = MutableLiveData<TruthDare>()
        viewModelScope.launch(Dispatchers.IO) {
            val randomDare = truthDaresRepository.getRandomDare()
            liveData.postValue(randomDare)
        }
        return liveData
    }
}
