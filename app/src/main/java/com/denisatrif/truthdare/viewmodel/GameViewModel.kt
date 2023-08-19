package com.denisatrif.truthdare.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.denisatrif.truthdare.db.model.Player
import com.denisatrif.truthdare.db.model.QuestionType
import com.denisatrif.truthdare.db.model.TruthDare
import com.denisatrif.truthdare.db.repos.TruthDareRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val truthDaresRepository: TruthDareRepository,
) : ViewModel() {

    var players = mutableListOf<Player>()

    fun getNextTruth(type: QuestionType): LiveData<TruthDare> {
        val liveData = MutableLiveData<TruthDare>()
        viewModelScope.launch(Dispatchers.IO) {
            val randomTruth = truthDaresRepository.getRandomTruth(type)
            liveData.postValue(randomTruth)
        }
        return liveData
    }

    fun getNextDare(type: QuestionType): LiveData<TruthDare> {
        val liveData = MutableLiveData<TruthDare>()
        viewModelScope.launch(Dispatchers.IO) {
            val randomDare = truthDaresRepository.getRandomDare(type)
            liveData.postValue(randomDare)
        }
        return liveData
    }
}
