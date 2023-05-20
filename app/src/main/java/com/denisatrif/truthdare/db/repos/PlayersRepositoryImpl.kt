package com.denisatrif.truthdare.db.repos

import com.denisatrif.truthdare.db.dao.PlayerDao
import com.denisatrif.truthdare.db.model.Player

class PlayersRepositoryImpl(private val playerDao: PlayerDao) : PlayersRepository {
    override fun getAllPlayers() = playerDao.getAll()
    override fun exists(name: String) = playerDao.exists(name)
    override fun addPlayer(player: Player) = playerDao.insertAll(listOf(player))
    override fun insertAll(players: List<Player>) = playerDao.insertAll(players)
    override fun deleteAll() = playerDao.nukeTable()
    override fun delete(player: Player) = playerDao.delete(player)
    override fun getCount() = playerDao.getCount()

    override fun getPlayerAt(current: Int): Player =
        playerDao.getPlayerAt(current)

}
