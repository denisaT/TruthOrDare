package com.denisatrif.truthdare.viewmodel

import androidx.lifecycle.ViewModel
import com.denisatrif.truthdare.db.model.Player
import com.denisatrif.truthdare.db.model.QuestionType
import com.denisatrif.truthdare.db.model.TruthDare
import com.denisatrif.truthdare.db.repos.TruthDareRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val truthDaresRepository: TruthDareRepository,
) : ViewModel() {

    var players = mutableListOf<Player>()

    fun getNextTruth(type: QuestionType): Flow<TruthDare> {
        return truthDaresRepository.getRandomDare(type)
    }

    fun getNextDare(type: QuestionType): Flow<TruthDare> {
        return truthDaresRepository.getRandomDare(type)
    }
}
