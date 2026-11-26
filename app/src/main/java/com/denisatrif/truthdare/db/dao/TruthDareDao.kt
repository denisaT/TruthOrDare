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

    @Query("SELECT * FROM truthdare WHERE truthDareType = 'TRUTH'")
    fun getAllTruths(): List<TruthDare>

    @Query("SELECT * FROM truthdare WHERE truthDareType = 'DARE'")
    fun getAllDares(): List<TruthDare>

    @Query("SELECT * FROM truthdare WHERE truthDareType = 'TRUTH' ORDER BY RANDOM() LIMIT 1")
    fun getRandomTruth(): TruthDare

    @Query("SELECT * FROM truthdare WHERE truthDareType = 'DARE' ORDER BY RANDOM() LIMIT 1")
    fun getRandomDare(): TruthDare

    @Query("SELECT * FROM truthdare WHERE truthDareType = 'TRUTH' AND id = :index")
    fun getTruthWithIndex(index: Int): TruthDare

    @Query("SELECT * FROM truthdare WHERE truthDareType = 'DARE' AND id = :index")
    fun getDareWithIndex(index: Int): TruthDare

    @Query("SELECT * FROM truthdare WHERE truthDareType = 'TRUTH' AND type = (:qType) ORDER BY RANDOM() LIMIT 1")
    fun getRandomTruth(qType: QuestionType): Flow<TruthDare>

    @Query("SELECT * FROM truthdare WHERE truthDareType = 'DARE' AND type = (:qType) ORDER BY RANDOM() LIMIT 1")
    fun getRandomDare(qType: QuestionType): Flow<TruthDare>

    @Query("SELECT * FROM truthdare WHERE truthDareType = 'TRUTH' AND type = (:qType) AND isFull = (:isFull) ORDER BY RANDOM() LIMIT 1")
    fun getRandomTruth(qType: QuestionType, isFull: Boolean): Flow<TruthDare>

    @Query("SELECT * FROM truthdare WHERE truthDareType = 'DARE' AND type = (:qType) AND isFull = (:isFull) ORDER BY RANDOM() LIMIT 1")
    fun getRandomDare(qType: QuestionType, isFull: Boolean): Flow<TruthDare>
}
