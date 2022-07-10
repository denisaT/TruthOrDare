package com.denisatrif.truthdare.db.repos

import com.denisatrif.truthdare.db.dao.TruthDareDao
import com.denisatrif.truthdare.db.model.QuestionType
import com.denisatrif.truthdare.db.model.TruthDare

class TruthDareRepository(private val truthDareDao: TruthDareDao) {
    fun getAll() = truthDareDao.getAll()
    fun getAllTruths() = truthDareDao.getAllTruths()
    fun getAllDares() = truthDareDao.getAllDares()

    fun insertAll(truthDares: List<TruthDare>) = truthDareDao.insertAll(truthDares)
    fun getRandomTruth(type: QuestionType) = truthDareDao.getRandomTruth(type)
    fun getRandomDare(type: QuestionType) = truthDareDao.getRandomDare(type)

    fun getTruthWithIndex(id: Int) = truthDareDao.getTruthWithIndex(id)
    fun getDareWithIndex(id: Int) = truthDareDao.getDareWithIndex(id)

    fun getRandomLiteTruth(type: QuestionType) = truthDareDao.getRandomTruth(type, false)
    fun getRandomLiteDare(type: QuestionType) = truthDareDao.getRandomDare(type, false)


}