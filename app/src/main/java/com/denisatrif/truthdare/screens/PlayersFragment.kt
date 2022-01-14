package com.denisatrif.truthdare.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.denisatrif.truthdare.R
import com.denisatrif.truthdare.databinding.FragmentPlayersBinding
import com.denisatrif.truthdare.db.AppDatabase
import com.denisatrif.truthdare.db.model.Player
import com.denisatrif.truthdare.db.repos.PlayersRepository
import com.denisatrif.truthdare.viewmodel.PlayersViewModel
import kotlin.random.Random

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class PlayersFragment : Fragment() {

    private var playersViewModel: PlayersViewModel? = null
    private var _binding: FragmentPlayersBinding? = null
    private var numberOfPlayers = 0

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayersBinding.inflate(inflater, container, false)
        playersViewModel =
            PlayersViewModel(PlayersRepository(AppDatabase.getInstance(context).playerDao()))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addPlayer.setOnClickListener {
            addNewPlayerLayout()
        }
        binding.startGame.setOnClickListener {
            playersViewModel?.addPlayer(Player(Random.nextInt(), "Playerutz"))
            findNavController().navigate(R.id.action_PlayersFragment_to_GameFragment)
        }
        binding.firstPlayerContainer.player = Player(9, "DENISA")
        binding.firstPlayerContainer.deleteNameButton.setOnClickListener {
            binding.firstPlayerContainer.playerEt.setText("")
        }

        binding.secondPlayerContainer.deleteNameButton.setOnClickListener {
            binding.secondPlayerContainer.playerContainer.visibility = GONE
        }
    }

    private fun addNewPlayerLayout() {
        numberOfPlayers++
        val newPlayerView = layoutInflater.inflate(R.layout.player_input, null)
        newPlayerView.findViewById<ImageButton>(R.id.delete_name_button).setOnClickListener {
            binding.namesContainer.removeView(newPlayerView)
        }
        binding.namesContainer.addView(newPlayerView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}