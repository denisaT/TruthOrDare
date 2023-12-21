package com.denisatrif.truthdare.db.model

import androidx.room.TypeConverters
import com.denisatrif.truthdare.db.Converters

@TypeConverters(Converters::class)
enum class TruthDareType {
    TRUTH, DARE
}
