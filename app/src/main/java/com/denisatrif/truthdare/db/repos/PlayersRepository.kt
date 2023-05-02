package com.denisatrif.truthdare.db.repos

import com.denisatrif.truthdare.db.model.Player

interface PlayersRepository {
    fun getAllPlayers(): List<Player>
    fun exists(name: String): Boolean
    fun addPlayer(player: Player): LongArray
    fun insertAll(players: List<Player>): LongArray
    fun deleteAll()
    fun delete(player: Player)
    fun getNext(current: Int): Player
}
