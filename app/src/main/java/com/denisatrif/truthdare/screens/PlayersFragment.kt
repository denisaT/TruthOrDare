package com.denisatrif.truthdare.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.size
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.denisatrif.truthdare.R
import com.denisatrif.truthdare.databinding.FragmentPlayersBinding
import com.denisatrif.truthdare.databinding.PlayerInputBinding
import com.denisatrif.truthdare.db.AppDatabase
import com.denisatrif.truthdare.db.model.Player
import com.denisatrif.truthdare.utils.setColorToVectorDrawable
import com.denisatrif.truthdare.viewmodel.PlayersViewModel
import com.denisatrif.truthdare.viewmodel.PlayersViewModelFactory
import kotlin.random.Random

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class PlayersFragment : Fragment() {
    private lateinit var playersViewModel: PlayersViewModel
    private var _binding: FragmentPlayersBinding? = null
    private var inputBindings: MutableList<PlayerInputBinding?>? = mutableListOf()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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
            }
        inputBindings = mutableListOf()
        playersViewModel.getAllPlayers().observe(binding.lifecycleOwner!!, {
            generatePlayerLayouts(it)
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addPlayer.setOnClickListener {
            addNewPlayerLayout()
        }
        binding.startGame.setOnClickListener {
            startGame()
        }

        binding.firstPlayerContainer.deleteNameButton.setOnClickListener {
            binding.firstPlayerContainer.playerEt.setText("")
        }

        binding.secondPlayerContainer.deleteNameButton.setOnClickListener {
            binding.secondPlayerContainer.playerEt.setText("")
        }
    }

    private fun startGame() {
        if (notEnoughPlayers()) {
            showNoPlayersDialog()
            return
        }
        binding.firstPlayerContainer.player?.name =
            binding.firstPlayerContainer.playerEt.text.toString()
        binding.secondPlayerContainer.player?.name =
            binding.secondPlayerContainer.playerEt.text.toString()

        playersViewModel.players.add(binding.firstPlayerContainer.player!!)
        playersViewModel.players.add(binding.secondPlayerContainer.player!!)
        for (i in inputBindings!!) {
            if (i?.playerEt?.text.toString().isNotBlank()) {
                playersViewModel.players.add(i?.player!!)
            }
        }
        playersViewModel.startGame()
        findNavController().navigate(R.id.action_PlayersFragment_to_GameFragment)
    }

    private fun notEnoughPlayers() =
        binding.namesContainer.size < 2 ||
                binding.firstPlayerContainer.playerEt.text.isEmpty() ||
                (binding.firstPlayerContainer.playerEt.text.isNotEmpty() &&
                        binding.secondPlayerContainer.playerEt.text.isEmpty())

    private fun showNoPlayersDialog() {
        val alertDialog: AlertDialog? = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setTitle(R.string.not_enough_players_title)
                setMessage(R.string.not_enough_players_message)
                setPositiveButton(
                    R.string.ok
                ) { dialog, _ ->
                    dialog.dismiss()
                }
            }
            builder.create()
        }
        alertDialog?.show()
    }

    private fun setGenderIcons(isFemale: Boolean, binding: PlayerInputBinding) {
        context?.setColorToVectorDrawable(
            binding.femaleIcon.drawable,
            if (isFemale) R.color.purple_500 else R.color.white
        )
        context?.setColorToVectorDrawable(
            binding.maleIcon.drawable,
            if (isFemale) R.color.white else R.color.purple_500
        )
    }

    private fun generatePlayerLayouts(players: List<Player>) {
        if (players.size >= 2) {
            binding.firstPlayerContainer.player = players[0]
            binding.secondPlayerContainer.player = players[1]

            //setGenderIcons(players[0].gender, binding.firstPlayerContainer)

            context?.setColorToVectorDrawable(
                binding.firstPlayerContainer.femaleIcon.drawable,
                if (players[0].gender) R.color.purple_500 else R.color.white
            )
            context?.setColorToVectorDrawable(
                binding.firstPlayerContainer.maleIcon.drawable,
                if (players[0].gender) R.color.white else R.color.purple_500
            )

            context?.setColorToVectorDrawable(
                binding.secondPlayerContainer.femaleIcon.drawable,
                if (players[0].gender) R.color.purple_500 else R.color.white
            )
            context?.setColorToVectorDrawable(
                binding.secondPlayerContainer.maleIcon.drawable,
                if (players[0].gender) R.color.white else R.color.purple_500
            )

            binding.firstPlayerContainer.callback = Callback { isFemale ->
                binding.firstPlayerContainer.player?.gender = isFemale
                context?.setColorToVectorDrawable(
                    binding.firstPlayerContainer.femaleIcon.drawable,
                    if (isFemale) R.color.purple_500 else R.color.white
                )
                context?.setColorToVectorDrawable(
                    binding.firstPlayerContainer.maleIcon.drawable,
                    if (isFemale) R.color.white else R.color.purple_500
                )
            }

            binding.secondPlayerContainer.callback = Callback { isFemale ->
                binding.secondPlayerContainer.player?.gender = isFemale
                context?.setColorToVectorDrawable(
                    binding.secondPlayerContainer.femaleIcon.drawable,
                    if (isFemale) R.color.purple_500 else R.color.white
                )
                context?.setColorToVectorDrawable(
                    binding.secondPlayerContainer.maleIcon.drawable,
                    if (isFemale) R.color.white else R.color.purple_500
                )
            }
            if (players.size > 2) {
                for (i in 2 until players.size) {
                    addExistingPlayerUI(players[i])
                }
            }
        } else if (players.isEmpty()) {
            binding.firstPlayerContainer.player = Player(Random.nextInt(), "", 1, false)
            binding.secondPlayerContainer.player = Player(Random.nextInt(), "", 2, false)

            context?.setColorToVectorDrawable(
                binding.firstPlayerContainer.femaleIcon.drawable,
                R.color.purple_500
            )
            context?.setColorToVectorDrawable(
                binding.firstPlayerContainer.maleIcon.drawable,
                R.color.white
            )
            context?.setColorToVectorDrawable(
                binding.secondPlayerContainer.femaleIcon.drawable,
                R.color.white
            )
            context?.setColorToVectorDrawable(
                binding.secondPlayerContainer.maleIcon.drawable,
                R.color.purple_500
            )

            binding.firstPlayerContainer.callback = Callback { isFemale ->
                binding.firstPlayerContainer.player?.gender = isFemale
                context?.setColorToVectorDrawable(
                    binding.firstPlayerContainer.femaleIcon.drawable,
                    if (isFemale) R.color.purple_500 else R.color.white
                )
                context?.setColorToVectorDrawable(
                    binding.firstPlayerContainer.maleIcon.drawable,
                    if (isFemale) R.color.white else R.color.purple_500
                )
            }

            binding.secondPlayerContainer.callback = Callback { isFemale ->
                binding.secondPlayerContainer.player?.gender = isFemale
                context?.setColorToVectorDrawable(
                    binding.secondPlayerContainer.femaleIcon.drawable,
                    if (isFemale) R.color.purple_500 else R.color.white
                )
                context?.setColorToVectorDrawable(
                    binding.secondPlayerContainer.maleIcon.drawable,
                    if (isFemale) R.color.white else R.color.purple_500
                )
            }

        }
    }

    private fun addExistingPlayerUI(player: Player) {
        val inputBinding = PlayerInputBinding.inflate(layoutInflater, binding.namesContainer, true)
        inputBindings?.add(inputBinding)
        inputBinding.player = player
        inputBinding.deleteNameButton.setOnClickListener {
            binding.namesContainer.removeView(inputBinding.root)
            inputBindings?.remove(inputBinding)
            playersViewModel.deletePlayer(inputBinding.player!!)
        }

        context?.setColorToVectorDrawable(
            inputBinding.femaleIcon.drawable,
            if (player.gender) R.color.purple_500 else R.color.white
        )
        context?.setColorToVectorDrawable(
            inputBinding.maleIcon.drawable,
            if (player.gender) R.color.white else R.color.purple_500
        )
        inputBinding.playerEt.apply {
            setText(player.name)
            doAfterTextChanged {
                inputBinding.player?.name = inputBinding.playerEt.text.toString()
            }
        }
        inputBinding.callback = Callback { isFemale ->
            inputBinding.player?.gender = isFemale
            context?.setColorToVectorDrawable(
                inputBinding.femaleIcon.drawable,
                if (isFemale) R.color.purple_500 else R.color.white
            )
            context?.setColorToVectorDrawable(
                inputBinding.maleIcon.drawable,
                if (isFemale) R.color.white else R.color.purple_500
            )
        }
    }

    private fun addNewPlayerLayout() {
        val inputBinding = PlayerInputBinding.inflate(layoutInflater, binding.namesContainer, true)
        inputBinding.player = Player(Random.nextInt(), "", inputBindings?.size?.plus(3)!!, false)
        inputBindings?.add(inputBinding)
        inputBinding.deleteNameButton.setOnClickListener {
            binding.namesContainer.removeView(inputBinding.root)
            inputBindings?.remove(inputBinding)
            playersViewModel.deletePlayer(inputBinding.player!!)
        }
        inputBinding.playerEt.doAfterTextChanged {
            inputBinding.player?.name = inputBinding.playerEt.text.toString()
        }
        inputBinding.callback = Callback { isFemale ->
            inputBinding.player?.gender = isFemale
            context?.setColorToVectorDrawable(
                inputBinding.femaleIcon.drawable,
                if (isFemale) R.color.purple_500 else R.color.white
            )
            context?.setColorToVectorDrawable(
                inputBinding.maleIcon.drawable,
                if (isFemale) R.color.white else R.color.purple_500
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        inputBindings?.clear()
        inputBindings = null
    }

    fun interface Callback {
        fun toggleGender(isFemale: Boolean)
    }
}