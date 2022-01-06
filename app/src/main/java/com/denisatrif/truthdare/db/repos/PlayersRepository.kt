package com.denisatrif.truthdare.db.repos

import com.denisatrif.truthdare.db.dao.PlayerDao
import com.denisatrif.truthdare.db.model.Player

class PlayersRepository(private val playerDao: PlayerDao) {
    fun getAllPlayers() = playerDao.getAll()
    fun addPlayer(player: Player) = playerDao.insertAll(player)
    fun insertAll(players: List<Player>) = playerDao.insertAll(*players.toTypedArray())
}
