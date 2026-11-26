package com.denisatrif.truthdare.utils

import android.content.Context
import com.denisatrif.truthdare.db.AppDatabase

object LanguageUtils {

    fun readByLanguage(context: Context) {
        Thread {
            //clean
            AppDatabase.getInstance(context).truthDareDao().nukeTable()

            //reinsert
            AppDatabase.getInstance(context).truthDareDao()
                .insertAll(CsvUtils.readDaresFromCsv(context))
            AppDatabase.getInstance(context).truthDareDao()
                .insertAll(CsvUtils.readTruthsFromCsv(context))
        }.start()
    }

}
