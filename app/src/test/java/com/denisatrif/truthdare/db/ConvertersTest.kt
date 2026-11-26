package com.denisatrif.truthdare.db

import com.denisatrif.truthdare.db.model.QuestionType
import com.denisatrif.truthdare.db.model.TruthDareType
import org.junit.Assert.assertEquals
import org.junit.Test

class ConvertersTest {

    private val converters = Converters()

    @Test
    fun questionType_roundTripsThroughString() {
        QuestionType.values().forEach { type ->
            assertEquals(type, converters.toQuestionType(converters.fromQuestionType(type)))
        }
    }

    @Test
    fun truthDareType_roundTripsThroughString() {
        TruthDareType.values().forEach { type ->
            assertEquals(type, converters.toTruthDareType(converters.fromTruthDareType(type)))
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun unknownQuestionType_throws() {
        converters.toQuestionType("UNKNOWN")
    }
}
