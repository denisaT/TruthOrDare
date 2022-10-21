package com.denisatrif.truthdare.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlin.random.Random

@Entity(indices = [Index(value = ["name"], unique = true)])
data class Player(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "name") var name: String?,
    @ColumnInfo(name = "order") var order: Int,
    @ColumnInfo(name = "gender") var gender: Boolean
) {
    companion object {
        fun getEmpty(
            id: Int = Random.nextInt(),
            name: String = "Denisa",
            order: Int = 1,
            gender: Boolean = true
        ) = Player(id, name, order, gender)
    }
}