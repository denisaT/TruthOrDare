package com.denisatrif.truthdare.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.denisatrif.truthdare.db.dao.PlayerDao
import com.denisatrif.truthdare.db.dao.TruthDareDao
import com.denisatrif.truthdare.db.model.Player
import com.denisatrif.truthdare.db.model.TruthDare

@Database(entities = [Player::class, TruthDare::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun playerDao(): PlayerDao
    abstract fun truthDareDao(): TruthDareDao

    companion object {

        private const val DB_NAME = "truthDareDB"
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context?): AppDatabase {
            synchronized(this) {
                if (instance == null) {
                    instance =
                        Room.databaseBuilder(context!!, AppDatabase::class.java, DB_NAME)
                            .build()
                }
                return instance!!
            }
        }

    }
}