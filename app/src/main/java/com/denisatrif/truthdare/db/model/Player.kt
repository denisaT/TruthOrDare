package com.denisatrif.truthdare.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Player(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "name") var name: String?
)