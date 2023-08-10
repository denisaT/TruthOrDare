package com.denisatrif.truthdare.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.denisatrif.truthdare.db.model.Player
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
    var partyModeTruthCounter = 0
    var partyModeDareCounter = 0

    fun getNextTruth(): LiveData<TruthDare> {
        val liveData = MutableLiveData<TruthDare>()
        viewModelScope.launch(Dispatchers.IO) {
            val randomTruth = truthDaresRepository.getTruthWithIndex(partyModeTruthCounter++)
            liveData.postValue(randomTruth)
        }
        return liveData
    }

    fun getNextDare(): LiveData<TruthDare> {
        val liveData = MutableLiveData<TruthDare>()
        viewModelScope.launch(Dispatchers.IO) {
            val randomTruth = truthDaresRepository.getDareWithIndex(partyModeDareCounter++)
            liveData.postValue(randomTruth)
        }
        return liveData
    }

}
