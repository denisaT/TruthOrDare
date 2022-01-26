package com.denisatrif.truthdare.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.denisatrif.truthdare.R
import com.denisatrif.truthdare.databinding.FragmentGameBinding
import com.denisatrif.truthdare.db.AppDatabase
import com.denisatrif.truthdare.db.model.QuestionType
import com.denisatrif.truthdare.screens.GameFragment.Callback
import com.denisatrif.truthdare.viewmodel.GameViewModel
import com.denisatrif.truthdare.viewmodel.GameViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.*


class GameFragment : Fragment() {

    private lateinit var gameViewModel: GameViewModel
    private var _binding: FragmentGameBinding? = null
    private var bottomSheetBehavior: BottomSheetBehavior<View>? = null
    private var questionType = QuestionType.PARTY

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        gameViewModel = ViewModelProvider(
            this,
            GameViewModelFactory(AppDatabase.getInstance(context))
        )[GameViewModel::class.java]
        _binding = FragmentGameBinding.inflate(inflater, container, false)
            .apply {
                lifecycleOwner = viewLifecycleOwner
                callback = Callback { isTruth ->
                    if (isTruth) {
                        gameViewModel.getRandomTruth(questionType).observe(lifecycleOwner!!, {
                            binding.questionContent.text = it.question
                            binding.player = gameViewModel.getNextPlayer()
                        })
                    } else {
                        gameViewModel.getRandomDare(questionType).observe(lifecycleOwner!!, {
                            binding.questionContent.text = it.question
                            binding.player = gameViewModel.getNextPlayer()
                        })
                    }
                }
                gameViewModel.getAllPlayers().observe(lifecycleOwner!!, {
                    gameViewModel.players = it.toMutableList()
                    binding.player = gameViewModel.getCurrentPlayer()
                })

            }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomSheetBehavior = from(binding.includedLayout.standardBottomSheet)
        bottomSheetBehavior!!.addBottomSheetCallback(bottomSheetCallback)
        bottomSheetBehavior!!.peekHeight = 200
        bottomSheetBehavior!!.state = STATE_EXPANDED

        disableMainScreen()

        binding.includedLayout.dirtyModeButton.setOnClickListener {
            questionType = QuestionType.DIRTY
            binding.includedLayout.modeTv.text = getString(R.string.dirty)
        }
        binding.includedLayout.sexyModeButton.setOnClickListener {
            questionType = QuestionType.SEXY
            binding.includedLayout.modeTv.text = getString(R.string.sexy)
        }
        binding.includedLayout.partyModeButton.setOnClickListener {
            questionType = QuestionType.PARTY
            binding.includedLayout.modeTv.text = getString(R.string.party)
        }
    }

    private val bottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if (newState == STATE_EXPANDED) {
                disableMainScreen()
            } else if (newState == STATE_COLLAPSED) {
                binding.dareButton.isEnabled = true
                binding.truthButton.isEnabled = true
                binding.gameScreenContainer.setBackgroundResource(R.drawable.game_container_background)
            } else if ((bottomSheetBehavior!!.state == STATE_EXPANDED ||
                        bottomSheetBehavior!!.state == STATE_COLLAPSED)
                && newState == STATE_DRAGGING
            ) {
                bottomSheetBehavior!!.state = STATE_COLLAPSED
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            //Toast.makeText(context, "onSlide", Toast.LENGTH_LONG).show()
        }
    }

    private fun disableMainScreen() {
        binding.dareButton.isEnabled = false
        binding.truthButton.isEnabled = false
        binding.gameScreenContainer.setBackgroundResource(R.color.purple_200_alpha)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun interface Callback {
        fun generateRandom(isTruth: Boolean)
    }

}