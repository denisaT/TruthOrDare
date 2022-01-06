package com.denisatrif.truthdare.db.repos

import com.denisatrif.truthdare.db.dao.TruthDareDao
import com.denisatrif.truthdare.db.model.TruthDare

class TruthDareRepository(private val truthDareDao: TruthDareDao) {
    fun getAll() = truthDareDao.getAll()
    fun getAllTruths() = truthDareDao.getAllTruths()
    fun getAllDares() = truthDareDao.getAllDares()

    //fun addTruthDare(truthDare: TruthDare) = truthDareDao.insertAll(truthDare)
    fun insertAll(truthDares: List<TruthDare>) = truthDareDao.insertAll(truthDares)
}