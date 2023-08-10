package com.denisatrif.truthdare.db

import androidx.room.TypeConverter
import com.denisatrif.truthdare.db.model.QuestionType
import com.denisatrif.truthdare.db.model.TruthDareType

class Converters {

    @TypeConverter
    fun fromQuestionType(type: QuestionType): String {
        return type.name
    }

    @TypeConverter
    fun toPriority(type: String): QuestionType {
        return QuestionType.valueOf(type)
    }

    @TypeConverter
    fun fromTruthDareType(truthDare: TruthDareType): String {
        return truthDare.name
    }
}
