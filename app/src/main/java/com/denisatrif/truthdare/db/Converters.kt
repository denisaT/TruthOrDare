package com.denisatrif.truthdare.db

import androidx.room.TypeConverter
import com.denisatrif.truthdare.db.model.QuestionType

class Converters {

    @TypeConverter
    fun fromQuestionType(type: QuestionType): String {
        return type.name
    }

    @TypeConverter
    fun toPriority(type: String): QuestionType {
        return QuestionType.valueOf(type)
    }
}