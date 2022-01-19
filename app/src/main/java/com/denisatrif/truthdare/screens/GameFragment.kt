package com.denisatrif.truthdare.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.denisatrif.truthdare.databinding.FragmentGameBinding
import com.denisatrif.truthdare.db.AppDatabase
import com.denisatrif.truthdare.screens.GameFragment.Callback
import com.denisatrif.truthdare.viewmodel.GameViewModel
import com.denisatrif.truthdare.viewmodel.GameViewModelFactory


class GameFragment : Fragment() {

    private lateinit var gameViewModel: GameViewModel
    private var _binding: FragmentGameBinding? = null

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
                        gameViewModel.getRandomTruth().observe(lifecycleOwner!!, {
                            binding.questionContent.text = it.question
                        })
                    } else {
                        gameViewModel.getRandomDare().observe(lifecycleOwner!!, {
                            binding.questionContent.text = it.question
                        })
                    }
                }

            }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun interface Callback {
        fun generateRandom(isTruth: Boolean)
    }

}