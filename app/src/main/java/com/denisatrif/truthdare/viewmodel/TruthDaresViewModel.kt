package com.denisatrif.truthdare.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.denisatrif.truthdare.db.model.TruthDare
import com.denisatrif.truthdare.db.repos.TruthDareRepository

class TruthDaresViewModel(
    private val truthDaresRepository: TruthDareRepository
) : ViewModel() {

    val allDares = truthDaresRepository.getAll().asLiveData()

    fun getRandomDare(): TruthDare? {
        val allDares = truthDaresRepository.getAllDares()
        return allDares.asLiveData().value?.random()
    }

    fun getRandomTruth(): TruthDare? {
        val allDares = truthDaresRepository.getAllTruths()
        return allDares.asLiveData().value?.random()
    }

    companion object {
    }
}