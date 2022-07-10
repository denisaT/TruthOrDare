package com.denisatrif.truthdare.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.denisatrif.truthdare.R
import com.denisatrif.truthdare.databinding.AddPlayerFieldBinding
import com.denisatrif.truthdare.databinding.FragmentPlayersBinding
import com.denisatrif.truthdare.db.AppDatabase
import com.denisatrif.truthdare.db.model.Player
import com.denisatrif.truthdare.viewmodel.PlayersViewModel
import com.denisatrif.truthdare.viewmodel.PlayersViewModelFactory


class PlayersFragment2 : Fragment() {

    private lateinit var playersViewModel: PlayersViewModel
    private var _binding: FragmentPlayersBinding? = null
    private var inputBindings: MutableList<AddPlayerFieldBinding?>? = mutableListOf()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // This callback will only be called when MyFragment is at least Started.
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            activity?.finish()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        playersViewModel = ViewModelProvider(
            this,
            PlayersViewModelFactory(AppDatabase.getInstance(context))
        )[PlayersViewModel::class.java]
        _binding = FragmentPlayersBinding.inflate(inflater, container, false)
            .apply {
                lifecycleOwner = viewLifecycleOwner
                callback = object : Callback {
                    override fun startGame() {
                        handleStartGame()
                    }

                    override fun addPlayer() {
                        addNewPlayerLayout()
                    }
                }
            }
        inputBindings = mutableListOf()
        inputBindings?.add(binding.newPlayerContainerFirst)
        playersViewModel.getAllPlayers().observe(binding.lifecycleOwner!!) {
            generatePlayerLayouts(it)
        }
        println("DENISA ---------------- ")
        val layout = binding.newPlayerContainerFirst.addANewPlayerView.root
        val observer = layout.viewTreeObserver
        observer.addOnGlobalLayoutListener { println(layout.height) }
        return binding.root
    }

    private fun generatePlayerLayouts(it: List<Player>?) {
        binding.newPlayerContainerFirst.callback = object : Callback {
            override fun toggleMale() {
                binding.newPlayerContainerFirst.addANewPlayerView.maleIcon.setImageResource(R.drawable.boy_2)
                binding.newPlayerContainerFirst.addANewPlayerView.femaleIcon.setImageResource(R.drawable.girl)
            }

            override fun toggleFemale() {
                binding.newPlayerContainerFirst.addANewPlayerView.maleIcon.setImageResource(R.drawable.boy)
                binding.newPlayerContainerFirst.addANewPlayerView.femaleIcon.setImageResource(R.drawable.girl_2)
            }
        }
    }

    private fun handleStartGame() {
        TODO("Not yet implemented")
    }

    private fun addNewPlayerLayout() {
        TODO("Not yet implemented")
    }

    interface Callback {
        fun toggleFemale() {}
        fun toggleMale() {}
        fun startGame() {}
        fun addPlayer() {}
    }

    open class ClickHandler {
        open fun handleQuestionClick(isTruth: Boolean) {}
        open fun handlePlayersClick() {}
        open fun handleLanguageClick() {}
        open fun handleNextPlayerClick() {}
    }
}