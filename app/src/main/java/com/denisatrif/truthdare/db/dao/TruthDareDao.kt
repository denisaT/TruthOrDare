package com.denisatrif.truthdare.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.denisatrif.truthdare.db.model.TruthDare
import kotlinx.coroutines.flow.Flow

@Dao
interface TruthDareDao {
    @Query("SELECT * FROM truthdare")
    fun getAll(): Flow<List<TruthDare>>

    @Query("SELECT * FROM truthdare WHERE id IN (:ids)")
    fun loadAllByIds(ids: IntArray): Flow<List<TruthDare>>

    @Insert
    fun insertAll(truths: List<TruthDare>)

    @Delete
    fun delete(truth: TruthDare)

    @Query("SELECT * FROM truthdare WHERE isTruth = 1")
    fun getAllTruths(): Flow<List<TruthDare>>

    @Query("SELECT * FROM truthdare WHERE isTruth = 0")
    fun getAllDares(): Flow<List<TruthDare>>
}
