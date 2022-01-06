package com.denisatrif.truthdare.utils

import android.content.Context
import android.util.Log
import com.denisatrif.truthdare.R
import com.denisatrif.truthdare.db.model.TruthDare
import com.denisatrif.truthdare.db.model.QuestionType
import com.opencsv.CSVReader
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.Reader
import kotlin.random.Random


object CsvUtils {

    private val TAG = CsvUtils.javaClass.simpleName

    fun readDaresFromCsv(context: Context): List<TruthDare> {
        Log.d(TAG, "READ dirty dares")
        val dares = arrayListOf<TruthDare>()
        try {
            var stream = context.resources.openRawResource(R.raw.dirty_dares)
            dares.addAll(
                loadFromCsv(
                    BufferedReader(InputStreamReader(stream, "UTF-8")),
                    QuestionType.DIRTY,
                    false
                )
            )
            stream = context.resources.openRawResource(R.raw.party_dares)
            dares.addAll(
                loadFromCsv(
                    BufferedReader(InputStreamReader(stream, "UTF-8")),
                    QuestionType.PARTY,
                    false
                )
            )
            stream = context.resources.openRawResource(R.raw.sexy_dares)
            dares.addAll(
                loadFromCsv(
                    BufferedReader(InputStreamReader(stream, "UTF-8")),
                    QuestionType.SEXY,
                    false
                )
            )
            stream.close()
        } catch (e: Exception) {
            Log.d(TAG, "Log exception")
            e.printStackTrace()
        }
        Log.d(TAG, "Dares size " + dares.size)
        return dares
    }

    fun readTruthsFromCsv(context: Context): List<TruthDare> {
        Log.d(TAG, "READ truths from CSV")
        val truths = arrayListOf<TruthDare>()
        try {
            var stream = context.resources.openRawResource(R.raw.dirty_truths)
            truths.addAll(
                loadFromCsv(
                    BufferedReader(InputStreamReader(stream, "UTF-8")),
                    QuestionType.DIRTY,
                    true
                )
            )
            stream = context.resources.openRawResource(R.raw.party_truths)
            truths.addAll(
                loadFromCsv(
                    BufferedReader(InputStreamReader(stream, "UTF-8")),
                    QuestionType.PARTY,
                    true
                )
            )
            stream = context.resources.openRawResource(R.raw.sexy_truths)
            truths.addAll(
                loadFromCsv(
                    BufferedReader(InputStreamReader(stream, "UTF-8")),
                    QuestionType.SEXY,
                    true
                )
            )
            stream.close()
        } catch (e: Exception) {
            Log.d(TAG, "Log exception")
            e.printStackTrace()
        }
        Log.d(TAG, "Truths size " + truths.size)
        return truths
    }


    private fun loadFromCsv(reader: Reader, type: QuestionType, isTruth: Boolean): List<TruthDare> {
        val csvReader = CSVReader(reader)
        val list = arrayListOf<TruthDare>()
        var line: Array<String>?
        do {
            line = csvReader.readNext() ?: break
            val dare = TruthDare(Random.nextInt(), question = line[0], type = type, isTruth = isTruth)
            list.add(dare)
        } while (line != null)
        reader.close()
        csvReader.close()
        return list
    }
}
