package com.denisatrif.truthdare.viewmodel

import com.denisatrif.truthdare.db.model.QuestionType
import com.denisatrif.truthdare.db.model.TruthDare
import com.denisatrif.truthdare.db.model.TruthDareType
import com.denisatrif.truthdare.db.repos.TruthDareRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GameViewModelTest {

    private val truth = TruthDare(1, true, "Your most embarrassing moment?", QuestionType.PARTY, TruthDareType.TRUTH)
    private val dare = TruthDare(2, true, "Sing a song", QuestionType.PARTY, TruthDareType.DARE)

    private val repository = FakeTruthDareRepository(truth, dare)
    private val viewModel = GameViewModel(repository)

    @Test
    fun getNextTruth_returnsTruthForRequestedType() = runBlocking {
        assertEquals(truth, viewModel.getNextTruth(QuestionType.PARTY).first())
        assertEquals(QuestionType.PARTY, repository.lastRequestedType)
    }

    @Test
    fun getNextDare_returnsDareForRequestedType() = runBlocking {
        assertEquals(dare, viewModel.getNextDare(QuestionType.DIRTY).first())
        assertEquals(QuestionType.DIRTY, repository.lastRequestedType)
    }
}

private class FakeTruthDareRepository(
    private val truth: TruthDare,
    private val dare: TruthDare,
) : TruthDareRepository {

    var lastRequestedType: QuestionType? = null

    override fun getAll() = listOf(truth, dare)
    override fun getAllTruths() = listOf(truth)
    override fun getAllDares() = listOf(dare)
    override fun insertAll(truthDares: List<TruthDare>) = Unit

    override fun getRandomTruth(type: QuestionType): Flow<TruthDare> {
        lastRequestedType = type
        return flowOf(truth)
    }

    override fun getRandomDare(type: QuestionType): Flow<TruthDare> {
        lastRequestedType = type
        return flowOf(dare)
    }

    override fun getTruthWithIndex(id: Int) = truth
    override fun getDareWithIndex(id: Int) = dare
    override fun getRandomLiteDare(type: QuestionType) = getRandomDare(type)
}
