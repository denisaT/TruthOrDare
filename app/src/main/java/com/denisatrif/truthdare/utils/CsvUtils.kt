package com.denisatrif.truthdare.utils

import android.content.Context
import android.util.Log
import androidx.annotation.IntegerRes
import com.denisatrif.truthdare.R
import com.denisatrif.truthdare.db.model.QuestionType
import com.denisatrif.truthdare.db.model.TruthDare
import com.opencsv.CSVReader
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.Reader
import kotlin.random.Random

object CsvUtils {

    private var key = 0;
    private val TAG = CsvUtils.javaClass.simpleName

    fun readDaresFromCsv(context: Context): List<TruthDare> {
        Log.d(TAG, "READ dirty dares")
        val dares = arrayListOf<TruthDare>()
        try {

            var stream = context.resources.openRawResource(
                +getLocalizedId(
                    context,
                    false,
                    QuestionType.DIRTY
                )
            )
            dares.addAll(
                loadFromCsv(
                    BufferedReader(InputStreamReader(stream, "UTF-8")),
                    QuestionType.DIRTY,
                    false
                )
            )
            stream = context.resources.openRawResource(
                +getLocalizedId(
                    context,
                    false,
                    QuestionType.PARTY
                )
            )
            dares.addAll(
                loadFromCsv(
                    BufferedReader(InputStreamReader(stream, "UTF-8")),
                    QuestionType.PARTY,
                    false
                )
            )
            stream = context.resources.openRawResource(
                +getLocalizedId(
                    context,
                    false,
                    QuestionType.SEXY
                )
            )
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
            var stream =
                context.resources.openRawResource(
                    +getLocalizedId(
                        context,
                        true,
                        QuestionType.DIRTY
                    )
                )
            truths.addAll(
                loadFromCsv(
                    BufferedReader(InputStreamReader(stream, "UTF-8")),
                    QuestionType.DIRTY,
                    true
                )
            )
            stream = context.resources.openRawResource(
                +getLocalizedId(
                    context,
                    true,
                    QuestionType.PARTY
                )
            )
            truths.addAll(
                loadFromCsv(
                    BufferedReader(InputStreamReader(stream, "UTF-8")),
                    QuestionType.PARTY,
                    true
                )
            )
            stream =
                context.resources.openRawResource(+getLocalizedId(context, true, QuestionType.SEXY))
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
            val dare =
                TruthDare(key++, list.size > 5, question = line[0], type = type, isTruth = isTruth)
            list.add(dare)
        } while (line != null)
        reader.close()
        csvReader.close()
        return list
    }

    @IntegerRes
    private fun getLocalizedId(context: Context, isTruth: Boolean, type: QuestionType): Int {
        val selectedLanguage = context.getString(R.string.lang)
        if (isTruth) {
            return when (selectedLanguage) {
                context.getString(R.string.english_language_code) ->
                    when (type) {
                        QuestionType.DIRTY -> R.raw.dirty_truths_en
                        QuestionType.PARTY -> R.raw.party_truths_en
                        QuestionType.SEXY -> R.raw.sexy_truths_en
                    }
                context.getString(R.string.romanian_language_code) ->
                    when (type) {
                        QuestionType.DIRTY -> R.raw.dirty_truths_ro
                        QuestionType.PARTY -> R.raw.party_truths_ro
                        QuestionType.SEXY -> R.raw.sexy_truths_ro
                    }

                context.getString(R.string.spanish_language_code) -> {
                    when (type) {
                        QuestionType.DIRTY -> R.raw.dirty_truths_es
                        QuestionType.PARTY -> R.raw.party_truths_es
                        QuestionType.SEXY -> R.raw.sexy_truths_es
                    }
                }
                else -> when (type) {
                    QuestionType.DIRTY -> R.raw.dirty_truths_en
                    QuestionType.PARTY -> R.raw.party_truths_en
                    QuestionType.SEXY -> R.raw.sexy_truths_en
                }
            }
        } else
            return when (selectedLanguage) {
                context.getString(R.string.english_language_code) ->
                    when (type) {
                        QuestionType.DIRTY -> R.raw.dirty_dares_en
                        QuestionType.PARTY -> R.raw.party_dares_en
                        QuestionType.SEXY -> R.raw.sexy_dares_en
                    }
                context.getString(R.string.romanian_language_code) ->
                    when (type) {
                        QuestionType.DIRTY -> R.raw.dirty_dares_ro
                        QuestionType.PARTY -> R.raw.party_dares_ro
                        QuestionType.SEXY -> R.raw.sexy_dares_ro
                    }

                context.getString(R.string.spanish_language_code) -> {
                    when (type) {
                        QuestionType.DIRTY -> R.raw.dirty_dares_es
                        QuestionType.PARTY -> R.raw.party_dares_es
                        QuestionType.SEXY -> R.raw.sexy_dares_es
                    }
                }
                else ->
                    when (type) {
                        QuestionType.DIRTY -> R.raw.dirty_dares_en
                        QuestionType.PARTY -> R.raw.party_dares_en
                        QuestionType.SEXY -> R.raw.sexy_dares_en
                    }
            }
    }
}

