package com.denisatrif.truthdare

import android.app.Application
import com.denisatrif.truthdare.db.AppDatabase
import com.denisatrif.truthdare.utils.CsvUtils

class App : Application() {

    private val truthDarePrefs = "TRUTH_DARE_PREFS"
    private val firstTime = "FIRST_TIME"

    override fun onCreate() {
        super.onCreate()

        val settings = getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE)
        if (settings.getBoolean(firstTime, true)) {
            Thread {
                AppDatabase.getInstance(applicationContext).truthDareDao()
                    .insertAll(CsvUtils.readDaresFromCsv(applicationContext))
                AppDatabase.getInstance(applicationContext).truthDareDao()
                    .insertAll(CsvUtils.readTruthsFromCsv(applicationContext))
            }.start()
            settings.edit().putBoolean(firstTime, false).apply()
        }
    }
}