package com.denisatrif.truthdare

import android.app.Application
import com.denisatrif.truthdare.db.AppDatabase
import com.denisatrif.truthdare.utils.CsvUtils
import com.unsplash.pickerandroid.photopicker.UnsplashPhotoPicker
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    private val firstTime = "FIRST_TIME"

    override fun onCreate() {
        super.onCreate()
        UnsplashPhotoPicker.init(
            this, // application
            "zLUcmuu4oNHaAGf4n9JbPYxqcVo4qZqssiMTP40ZisA",
            "Mpijv9CTiXiDszDVJVglRRny0S3_TQI6-iwQ-4nA90w"
        )

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