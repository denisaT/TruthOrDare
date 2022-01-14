package com.denisatrif.truthdare.viewmodel

import androidx.lifecycle.ViewModel
import com.denisatrif.truthdare.db.repos.TruthDareRepository

class TruthDaresViewModel(
    private val truthDaresRepository: TruthDareRepository
) : ViewModel() {

}