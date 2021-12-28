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

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class PlayersFragment : Fragment() {

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
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addPlayer.setOnClickListener {
            addNewPlayer()
        }
        binding.startGame.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        binding.firstPlayerContainer.playerEt.setText("DENISA")
        binding.secondPlayerContainer.playerEt.setText("CRISTINA")
        binding.firstPlayerContainer.deleteNameButton.setOnClickListener {
            binding.firstPlayerContainer.playerEt.setText("")
        }

        binding.secondPlayerContainer.deleteNameButton.setOnClickListener {
            binding.secondPlayerContainer.playerContainer.visibility = GONE
        }
    }

    private fun addNewPlayer() {
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