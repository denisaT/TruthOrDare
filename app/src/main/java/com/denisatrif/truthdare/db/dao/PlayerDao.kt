package com.denisatrif.truthdare.db.dao

import androidx.room.*
import com.denisatrif.truthdare.db.model.Player
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {
    @Query("SELECT * FROM player ORDER by `order`")
    fun getAll(): Flow<List<Player>>

    @Query("SELECT * FROM player WHERE id IN (:playerIds)")
    fun loadAllByIds(playerIds: IntArray): Flow<List<Player>>

    @Query(
        "SELECT * FROM player WHERE name LIKE :name LIMIT 1"
    )
    fun findByName(name: String): Flow<Player>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(players: List<Player>): LongArray

    @Delete
    fun delete(player: Player)

    @Query("SELECT EXISTS(SELECT * FROM player WHERE name = :name)")
    fun exists(name: String): Flow<Boolean>

    @Query("DELETE FROM player")
    fun nukeTable()

    @Query("SELECT * FROM player WHERE `order` = :current LIMIT 1")
    fun getPlayerAt(current: Int): Flow<Player>

    @Query("SELECT id FROM player")
    fun getListOfIds(): Flow<List<Int>>

    @Query("SELECT * FROM player WHERE `id` = :id LIMIT 1")
    fun getPlayerWithId(id: Int): Flow<Player>

    @Query("SELECT COUNT(*) FROM player")
    fun getCount(): Flow<Int>

}
