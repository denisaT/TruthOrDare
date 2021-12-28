package com.denisatrif.truthdare.db

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Truth(
    @PrimaryKey
    var _id: String = "",
    var question: String = "",
) : RealmObject() {
    var type: QuestionType
        get() {
            return QuestionType.valueOf(enumDescription)
        }
        set(newType) {
            enumDescription = type.name
        }
    private var enumDescription: String = QuestionType.PARTY.name
}