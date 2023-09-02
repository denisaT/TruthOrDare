package com.denisatrif.truthdare.db.repos

import com.denisatrif.truthdare.db.model.Player
import kotlinx.coroutines.flow.Flow

interface PlayersRepository {
    fun getAllPlayers(): List<Player>
    fun exists(name: String): Boolean
    fun addPlayer(player: Player): LongArray
    fun insertAll(players: List<Player>): LongArray
    fun deleteAll()
    fun delete(player: Player)
    fun getCount(): Int
    fun getPlayerAt(current: Int): Player
    fun getListOfIds(): Flow<List<Int>>
    fun getPlayerWithId(id: Int): Flow<Player>
}
