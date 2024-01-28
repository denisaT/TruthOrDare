package com.denisatrif.truthdare

import android.app.Application
import com.denisatrif.truthdare.billing.BillingClientWrapper
import com.denisatrif.truthdare.db.AppDatabase
import com.denisatrif.truthdare.utils.CsvUtils
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    private val firstTime = "FIRST_TIME"
    private val appOpened = "APP_OPENED"

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

        //TODO query billing at startup
        if (settings.getBoolean(appOpened, true)) {
            updateWithPurchases()
        }
    }

    private fun updateWithPurchases() {
        val billingClientWrapper = BillingClientWrapper(this)
        billingClientWrapper.startConnection()
    }
}
