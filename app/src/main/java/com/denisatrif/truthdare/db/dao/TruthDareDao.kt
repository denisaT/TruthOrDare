package com.denisatrif.truthdare.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.denisatrif.truthdare.db.model.QuestionType
import com.denisatrif.truthdare.db.model.TruthDare
import kotlinx.coroutines.flow.Flow

@Dao
interface TruthDareDao {
    @Query("SELECT * FROM truthdare")
    fun getAll(): List<TruthDare>

    @Query("SELECT * FROM truthdare WHERE id IN (:ids)")
    fun loadAllByIds(ids: IntArray): List<TruthDare>

    @Insert
    fun insertAll(truths: List<TruthDare>)

    @Delete
    fun delete(truth: TruthDare)

    @Query("DELETE FROM truthdare")
    fun nukeTable()

    @Query("SELECT * FROM truthdare WHERE isTruth = 1")
    fun getAllTruths(): Flow<List<TruthDare>>

    @Query("SELECT * FROM truthdare WHERE isTruth = 0")
    fun getAllDares(): List<TruthDare>

    @Query("SELECT * FROM truthdare WHERE isTruth = 1 ORDER BY RANDOM() LIMIT 1")
    fun getRandomTruth(): TruthDare

    @Query("SELECT * FROM truthdare WHERE isTruth = 0 ORDER BY RANDOM() LIMIT 1")
    fun getRandomDare(): TruthDare

    @Query("SELECT * FROM truthdare WHERE isTruth = 1 AND type = (:qType) ORDER BY RANDOM() LIMIT 1")
    fun getRandomTruth(qType: QuestionType): TruthDare

    @Query("SELECT * FROM truthdare WHERE isTruth = 0 AND type = (:qType) ORDER BY RANDOM() LIMIT 1")
    fun getRandomDare(qType: QuestionType): TruthDare
}
