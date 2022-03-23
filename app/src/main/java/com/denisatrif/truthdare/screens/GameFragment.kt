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
import com.denisatrif.truthdare.preferences.UserPreferences
import com.denisatrif.truthdare.screens.GameFragment.Dismisser
import com.denisatrif.truthdare.utils.CsvUtils
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
                        gameViewModel.incrementCounter(questionType)
                        fillWithRandom(isTruth)
                    }

                    override fun handlePlayersClick() {
                        findNavController().navigate(R.id.action_GameFragment_to_PlayersFragment)
                    }

                    override fun handleLanguageClick() {
                        val savedLanguageCode =
                            sharedPref.getString(UserPreferences.SAVED_LANGUAGE_KEY, "en")
                        showLanguageChooserDialog(savedLanguageCode)
                    }

                    override fun handleNextPlayerClick() {
                        if (gameViewModel.isLimitReachedForType(questionType)) {
                            showPurchaseModeDialog()
                        }
                        setButtonsVisibility(true)
                        binding.player = gameViewModel.getNextPlayer()
                        binding.questionContent.text = getString(
                            R.string.next_player_name,
                            gameViewModel.getCurrentPlayer().name
                        )
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
                    binding.questionContent.text = getString(
                        R.string.next_player_name,
                        gameViewModel.getCurrentPlayer().name
                    )
                }

            }
        return binding.root
    }

    private fun showPurchaseModeDialog() {
        val dialog = AlertDialog.Builder(ContextThemeWrapper(context, R.style.GenericAlertDialog))
            .apply {
                setTitle(R.string.mode_limit_reached)
                setCancelable(true)
                setNegativeButton(android.R.string.cancel, null)
                setPositiveButton(R.string.purchase) { _, _ ->
                }
            }.create().show()
    }

    private fun initPurchase() {
        //TDBillingClient().
//        val flowParams = BillingFlowParams.newBuilder()
//            .setSkuDetails(skuDetails)
//            .build()
//        val responseCode = billingClient.launchBillingFlow(activity, flowParams).responseCode

    }

    private fun fillWithRandom(isTruth: Boolean) {
        binding.contentCard.visibility = VISIBLE
        setButtonsVisibility(false)
        if (isTruth) {
            gameViewModel.getRandomTruth(questionType).observe(binding.lifecycleOwner!!) {
                binding.questionContent.text = it.question
            }
        } else {
            gameViewModel.getRandomDare(questionType).observe(binding.lifecycleOwner!!) {
                binding.questionContent.text = it.question
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButtonsVisibility(true)
        binding.playerName.visibility = GONE

        bottomSheetBehavior = from(binding.includedLayout.standardBottomSheet)
        bottomSheetBehavior!!.addBottomSheetCallback(bottomSheetCallback)
        bottomSheetBehavior!!.state = STATE_EXPANDED
        binding.truthButton.isEnabled = false
        binding.dareButton.isEnabled = false
        bottomSheetBehavior!!.isHideable = false
        setAlphaTo(0.5f)

        binding.includedLayout.dirtyModeButton.setOnClickListener {
            sharedPref.edit().putString(UserPreferences.GAME_MODE, QuestionType.DIRTY.name).apply()
            questionType = QuestionType.DIRTY
            binding.includedLayout.modeTv.text = getString(R.string.dirty_mode)
            bottomSheetBehavior!!.state = STATE_COLLAPSED
        }
        binding.includedLayout.sexyModeButton.setOnClickListener {
            sharedPref.edit().putString(UserPreferences.GAME_MODE, QuestionType.SEXY.name).apply()
            questionType = QuestionType.SEXY
            binding.includedLayout.modeTv.text = getString(R.string.sexy_mode)
            bottomSheetBehavior!!.state = STATE_COLLAPSED
        }
        binding.includedLayout.partyModeButton.setOnClickListener {
            sharedPref.edit().putString(UserPreferences.GAME_MODE, QuestionType.PARTY.name).apply()
            questionType = QuestionType.PARTY
            binding.includedLayout.modeTv.text = getString(R.string.party_mode)
            bottomSheetBehavior!!.state = STATE_COLLAPSED
        }

        if (sharedPref.getBoolean(UserPreferences.IS_LOCALE_CHANGED, false)) {
            Thread {
                AppDatabase.getInstance(requireContext()).truthDareDao().nukeTable()
                AppDatabase.getInstance(requireContext()).truthDareDao()
                    .insertAll(CsvUtils.readDaresFromCsv(requireContext()))
                AppDatabase.getInstance(requireContext()).truthDareDao()
                    .insertAll(CsvUtils.readTruthsFromCsv(requireContext()))
            }.start()
            sharedPref.edit().putBoolean(UserPreferences.IS_LOCALE_CHANGED, false).apply()
        }
    }

    private val bottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {

        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if ((bottomSheetBehavior!!.state == STATE_EXPANDED ||
                        bottomSheetBehavior!!.state == STATE_COLLAPSED)
                && newState == STATE_DRAGGING
            ) {
                bottomSheetBehavior!!.state = STATE_COLLAPSED
            }
            if (newState == STATE_COLLAPSED &&
                binding.includedLayout.modeTv.text.equals(getString(R.string.choose_your_mode))
            ) {
                binding.includedLayout.modeTv.text = getString(R.string.party_mode)
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            if (isAdded && slideOffset < 0.5) {
                setAlphaTo(1 - slideOffset)
            }
            binding.truthButton.isEnabled = slideOffset < 0.5
            binding.dareButton.isEnabled = slideOffset < 0.5
        }
    }

    private fun setAlphaTo(alpha: Float) {
        binding.contentCard.alpha = alpha
        binding.buttonsContainer.alpha = alpha
        binding.textviewFirst.alpha = alpha
    }

    private fun setButtonsVisibility(showGameButtons: Boolean) {
        binding.playerName.visibility = if (showGameButtons) GONE else VISIBLE
        binding.truthButton.visibility = if (showGameButtons) VISIBLE else GONE
        binding.dareButton.visibility = if (showGameButtons) VISIBLE else GONE
        binding.nextButtonPlayer.visibility = if (showGameButtons) GONE else VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    open class ClickHandler {
        open fun handleQuestionClick(isTruth: Boolean) {}
        open fun handlePlayersClick() {}
        open fun handleLanguageClick() {}
        open fun handleNextPlayerClick() {}
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
                        R.id.romanian -> updateAppLanguage(getString(R.string.romanian_language_code))
                        R.id.english -> updateAppLanguage(getString(R.string.english_language_code))
                        R.id.spanish -> updateAppLanguage(getString(R.string.spanish_language_code))
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
        sharedPref.edit().putString(UserPreferences.SAVED_LANGUAGE_KEY, language).apply()
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = context?.resources?.configuration
        config?.setLocale(locale)
        context?.createConfigurationContext(config!!)
        context?.resources?.updateConfiguration(config, context?.resources?.displayMetrics)
        activity?.recreate()
        sharedPref.edit().putBoolean(UserPreferences.IS_LOCALE_CHANGED, true).apply()
    }


}