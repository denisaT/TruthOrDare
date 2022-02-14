package com.denisatrif.truthdare.screens

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.denisatrif.truthdare.R
import com.denisatrif.truthdare.databinding.FragmentGameBinding
import com.denisatrif.truthdare.db.AppDatabase
import com.denisatrif.truthdare.db.model.QuestionType
import com.denisatrif.truthdare.screens.GameFragment.Dismisser
import com.denisatrif.truthdare.viewmodel.GameViewModel
import com.denisatrif.truthdare.viewmodel.GameViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.*
import java.util.*


class GameFragment : Fragment() {

    private lateinit var gameViewModel: GameViewModel
    private var _binding: FragmentGameBinding? = null
    private var bottomSheetBehavior: BottomSheetBehavior<View>? = null
    private var questionType = QuestionType.PARTY
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
        gameViewModel = ViewModelProvider(
            this,
            GameViewModelFactory(AppDatabase.getInstance(context))
        )[GameViewModel::class.java]
        _binding = FragmentGameBinding.inflate(inflater, container, false)
            .apply {
                lifecycleOwner = viewLifecycleOwner
                clickHandler = object : ClickHandler() {
                    override fun handleQuestionClick(isTruth: Boolean) {
                        generateRandom(isTruth)
                    }

                    override fun handlePlayersClick() {
                        findNavController().navigate(R.id.action_GameFragment_to_PlayersFragment)
                    }

                    override fun handleLanguageClick() {
                        val savedLanguageCode =
                            sharedPref?.getString(SettingsFragment.SAVED_LANGUAGE_KEY, "en")
                        showLanguageChooserDialog(savedLanguageCode)
                    }

                }
                dismisser = Dismisser {
                    if (bottomSheetBehavior != null &&
                        bottomSheetBehavior?.state == STATE_EXPANDED ||
                        bottomSheetBehavior?.state == STATE_DRAGGING
                    ) {
                        bottomSheetBehavior!!.state = STATE_COLLAPSED
                    }
                }
                gameViewModel.getAllPlayers().observe(lifecycleOwner!!) {
                    gameViewModel.players = it.toMutableList()
                    binding.player = gameViewModel.getCurrentPlayer()
                }

            }
        return binding.root
    }

    private fun generateRandom(isTruth: Boolean) {
        binding.contentCard.visibility = VISIBLE
        if (isTruth) {
            gameViewModel.getRandomTruth(questionType).observe(binding.lifecycleOwner!!) {
                binding.questionContent.text = it.question
                binding.player = gameViewModel.getNextPlayer()
            }
        } else {
            gameViewModel.getRandomDare(questionType).observe(binding.lifecycleOwner!!) {
                binding.questionContent.text = it.question
                binding.player = gameViewModel.getNextPlayer()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottomSheetBehavior = from(binding.includedLayout.standardBottomSheet)
        bottomSheetBehavior!!.addBottomSheetCallback(bottomSheetCallback)
        bottomSheetBehavior!!.peekHeight = 200
        bottomSheetBehavior!!.state = STATE_EXPANDED
        bottomSheetBehavior!!.isHideable = false

        setMainScreenTo(false)

        binding.includedLayout.dirtyModeButton.setOnClickListener {
            questionType = QuestionType.DIRTY
            binding.includedLayout.modeTv.text = getString(R.string.dirty_mode)
        }
        binding.includedLayout.sexyModeButton.setOnClickListener {
            questionType = QuestionType.SEXY
            binding.includedLayout.modeTv.text = getString(R.string.sexy_mode)
        }
        binding.includedLayout.partyModeButton.setOnClickListener {
            questionType = QuestionType.PARTY
            binding.includedLayout.modeTv.text = getString(R.string.party_mode)
        }
    }

    private val bottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {

        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if (newState == STATE_EXPANDED) {
                setMainScreenTo(false)
            } else if (newState == STATE_COLLAPSED) {
                setMainScreenTo(true)
            } else if ((bottomSheetBehavior!!.state == STATE_EXPANDED ||
                        bottomSheetBehavior!!.state == STATE_COLLAPSED)
                && newState == STATE_DRAGGING
            ) {
                bottomSheetBehavior!!.state = STATE_COLLAPSED
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {
        }
    }

    private fun setMainScreenTo(visible: Boolean) {
        binding.dareButton.isEnabled = visible
        binding.truthButton.isEnabled = visible
        binding.contentCard.visibility =
            if (visible && binding.questionContent.text.isNotEmpty()) VISIBLE else GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    open class ClickHandler {
        open fun handleQuestionClick(isTruth: Boolean) {}
        open fun handlePlayersClick() {}
        open fun handleLanguageClick() {}
    }

    fun interface Dismisser {
        fun dismiss()
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
        val dialog = AlertDialog.Builder(ContextThemeWrapper(context, R.style.GenericAlertDialog))
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
            }.create()

        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                .setTextColor(ContextCompat.getColor(requireContext(), R.color.black))

        }
        dialog.show()
        return view
    }

    private fun updateAppLanguage(language: String) {
        sharedPref.edit().putString(SettingsFragment.SAVED_LANGUAGE_KEY, language).apply()
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = context?.resources?.configuration
        config?.setLocale(locale)
        context?.createConfigurationContext(config!!)
        context?.resources?.updateConfiguration(config, context?.resources?.displayMetrics)
        activity?.recreate()
    }
}