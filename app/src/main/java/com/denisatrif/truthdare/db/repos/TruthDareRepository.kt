package com.denisatrif.truthdare.db.repos

import com.denisatrif.truthdare.db.model.QuestionType
import com.denisatrif.truthdare.db.model.TruthDare
import kotlinx.coroutines.flow.Flow

interface TruthDareRepository {
    fun getAll(): List<TruthDare>
    fun getAllTruths(): List<TruthDare>
    fun getAllDares(): List<TruthDare>

    fun insertAll(truthDares: List<TruthDare>)
    fun getRandomTruth(type: QuestionType): Flow<TruthDare>
    fun getRandomDare(type: QuestionType): Flow<TruthDare>

    fun getTruthWithIndex(id: Int): TruthDare
    fun getDareWithIndex(id: Int): TruthDare

    fun getRandomLiteDare(type: QuestionType): Flow<TruthDare>
}
