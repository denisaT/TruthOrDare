package com.denisatrif.truthdare.db.dao

import androidx.room.*
import com.denisatrif.truthdare.db.model.Player

@Dao
interface PlayerDao {
    @Query("SELECT * FROM player ORDER by `order`")
    fun getAll(): List<Player>

    @Query("SELECT * FROM player WHERE id IN (:playerIds)")
    fun loadAllByIds(playerIds: IntArray): List<Player>

    @Query(
        "SELECT * FROM player WHERE name LIKE :name LIMIT 1"
    )
    fun findByName(name: String): Player

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(players: List<Player>): LongArray

    @Delete
    fun delete(player: Player)

    @Query("SELECT EXISTS(SELECT * FROM player WHERE name = :name)")
    fun exists(name: String): Boolean

    @Query("DELETE FROM player")
    fun nukeTable()

    @Query("SELECT * FROM player WHERE `order` = :current LIMIT 1")
    fun getNext(current: Int): Player
}
