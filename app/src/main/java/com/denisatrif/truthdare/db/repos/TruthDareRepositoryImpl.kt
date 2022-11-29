package com.denisatrif.truthdare.db.repos

import com.denisatrif.truthdare.db.dao.TruthDareDao
import com.denisatrif.truthdare.db.model.QuestionType
import com.denisatrif.truthdare.db.model.TruthDare

class TruthDareRepositoryImpl(private val truthDareDao: TruthDareDao): TruthDareRepository {
    override fun getAll() = truthDareDao.getAll()
    override fun getAllTruths() = truthDareDao.getAllTruths()
    override fun getAllDares() = truthDareDao.getAllDares()

    override fun insertAll(truthDares: List<TruthDare>) = truthDareDao.insertAll(truthDares)
    override fun getRandomTruth(type: QuestionType) = truthDareDao.getRandomTruth(type)
    override fun getRandomDare(type: QuestionType) = truthDareDao.getRandomDare(type)

    override fun getTruthWithIndex(id: Int) = truthDareDao.getTruthWithIndex(id)
    override fun getDareWithIndex(id: Int) = truthDareDao.getDareWithIndex(id)

    override fun getRandomLiteTruth(type: QuestionType) = truthDareDao.getRandomTruth(type, false)
    override fun getRandomLiteDare(type: QuestionType) = truthDareDao.getRandomDare(type, false)


}