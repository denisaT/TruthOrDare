package com.denisatrif.truthdare.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.denisatrif.truthdare.db.Converters

@Entity
class TruthDare(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "isFull")
    val isFull: Boolean,
    @ColumnInfo(name = "question")
    var question: String?,
    @ColumnInfo(name = "type")
    @TypeConverters(Converters::class)
    var type: QuestionType?,
    @ColumnInfo(name = "isTruth")
    var isTruth: Boolean?
)