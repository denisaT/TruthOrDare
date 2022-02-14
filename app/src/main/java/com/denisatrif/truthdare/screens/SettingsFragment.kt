package com.denisatrif.truthdare.screens

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import com.denisatrif.truthdare.R
import com.denisatrif.truthdare.databinding.FragmentSettingsBinding
import java.util.*


class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private lateinit var sharedPref: SharedPreferences

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        sharedPref = activity?.getSharedPreferences(
            getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        )!!
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
            .apply {
                lifecycleOwner = viewLifecycleOwner
                clickHandler = object : ClickHandler() {
                    override fun handleLanguageClick() {
                        val savedLanguageCode = sharedPref?.getString(SAVED_LANGUAGE_KEY, "en")
                        showLanguageChooserDialog(savedLanguageCode)
                    }
                }
            }

        return binding.root

    }

    private fun showLanguageChooserDialog(savedLanguageCode: String?): View {
        val view = layoutInflater.inflate(R.layout.dialog_language_selection, null)
        val rg = view.findViewById<RadioGroup>(R.id.language_radio_group)
        val count = rg.childCount
        for (i in 0 until count) {
            val o: View = rg.getChildAt(i)
            if (o is RadioButton && o.tag == savedLanguageCode) {
                o.isChecked = true
            }
        }
        AlertDialog.Builder(ContextThemeWrapper(context, R.style.Theme_AppCompat_Light_Dialog))
            .apply {
                setTitle(R.string.choose_language)
                setCancelable(true)
                setView(view)
                setNegativeButton(android.R.string.cancel, null)
                setPositiveButton(R.string.ok) { _, _ ->
                    val rg = view.findViewById<RadioGroup>(R.id.language_radio_group)
                    when (rg.checkedRadioButtonId) {
                        R.id.romanian -> updateAppLanguage("ro")
                        R.id.english -> updateAppLanguage("en")
                        R.id.spanish -> updateAppLanguage("es")
                    }
                }
                create()
                show()
            }
        return view
    }

    private fun updateAppLanguage(language: String) {
        sharedPref.edit().putString(SAVED_LANGUAGE_KEY, language).apply()
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = context?.resources?.configuration
        config?.setLocale(locale)
        context?.createConfigurationContext(config!!)
        context?.resources?.updateConfiguration(config, context?.resources?.displayMetrics)
        activity?.recreate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    open class ClickHandler {
        open fun handleLanguageClick() {}
    }

    companion object {
        const val SAVED_LANGUAGE_KEY = "SAVED_LANGUAGE_KEY"
    }

}