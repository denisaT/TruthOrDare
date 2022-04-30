package com.denisatrif.truthdare.db.model

import androidx.room.TypeConverters
import com.denisatrif.truthdare.db.Converters

@TypeConverters(Converters::class)
enum class QuestionType(val id: String) {
    DIRTY("truth_dare_dirty_pack"),
    PARTY("truth_dare_party_pack"),
    SEXY("truth_dare_sexy_pack")
}