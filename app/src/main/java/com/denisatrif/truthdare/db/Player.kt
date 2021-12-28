package com.denisatrif.truthdare.db

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Player(
    @PrimaryKey
    var _id: String = "",
    var name: String = ""
) : RealmObject()