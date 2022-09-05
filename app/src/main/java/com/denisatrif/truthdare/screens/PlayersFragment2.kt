package com.denisatrif.truthdare.screens

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.denisatrif.truthdare.R
import com.denisatrif.truthdare.databinding.AddPlayerFieldBinding
import com.denisatrif.truthdare.databinding.FragmentPlayersBinding
import com.denisatrif.truthdare.databinding.PlayerFieldBinding
import com.denisatrif.truthdare.db.AppDatabase
import com.denisatrif.truthdare.db.model.Player
import com.denisatrif.truthdare.viewmodel.PlayersViewModel
import com.denisatrif.truthdare.viewmodel.PlayersViewModelFactory
import kotlin.random.Random


class PlayersFragment2 : Fragment() {

    private lateinit var playersViewModel: PlayersViewModel
    private var _binding: FragmentPlayersBinding? = null
    private var playerFieldBinding: PlayerFieldBinding? = null
    private var addNewPlayerBinding: AddPlayerFieldBinding? = null
    private var inputBindings: MutableList<PlayerFieldBinding?>? = mutableListOf()


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
                }
            }

        playerFieldBinding = _binding!!.addPlayerContainer.playerField
            .apply {
                callback = object : Callback {
                    override fun toggleFemale() {
                        playerFieldBinding?.maleIcon?.setImageResource(
                            R.drawable.boy_2
                        )
                        playerFieldBinding?.femaleIcon?.setImageResource(
                            R.drawable.girl
                        )
                    }

                    override fun toggleMale() {
                        binding.addPlayerContainer.playerField.maleIcon.setImageResource(
                            R.drawable.boy
                        )
                        binding.addPlayerContainer.playerField.femaleIcon.setImageResource(
                            R.drawable.girl_2
                        )
                    }

                    override fun expandAddPlayer() {
                        expandAddPlayerView()
                    }
                }
            }
        playerFieldBinding!!.deleteIcon.visibility = View.GONE

        addNewPlayerBinding = binding.addPlayerContainer
            .apply {
                callback = object : Callback {

                    override fun addPlayer() {
                        addNewPlayerLayout()
                    }
                }
            }

        val layout = binding.addPlayerContainer.playerField.root
        val observer = layout.viewTreeObserver
        observer.addOnGlobalLayoutListener { println(layout.height) }
        return binding.root
    }

    private fun handleStartGame() {
        TODO("Not yet implemented")
    }

    private fun addNewPlayerLayout() {
        binding.playersScrollView.visibility = View.VISIBLE
        binding.playersContainer.visibility = View.VISIBLE
        binding.addPlayerContainer.playerField.deleteIcon.setImageResource(R.drawable.plus)
        binding.addPlayerContainer.playerField.deleteIcon.tag = "plus"
        binding.addPlayerContainer.playerField.deleteIcon.visibility = View.VISIBLE

        val inputBinding =
            PlayerFieldBinding.inflate(layoutInflater, binding.playersScrollView, true)
        inputBinding.playerFieldContainer.animate().alpha(1.0f)

        inputBinding.player = Player(Random.nextInt(), "", inputBindings?.size?.plus(3)!!, false)
        inputBindings?.add(inputBinding)
        val param = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        param.setMargins(16, 16, 16, 16)
        inputBinding.playerFieldContainer.layoutParams = param
        inputBinding.deleteIcon.setImageResource(R.drawable.minus)
        inputBinding.deleteIcon.tag = "minus"
        inputBinding.playerFieldContainer.background =
            resources.getDrawable(R.drawable.transparent_button_shape, null)
        inputBinding.playerNameTv.text = binding.addPlayerContainer.playerNameEt.text
        binding.addPlayerContainer.playerNameEt.setText("")

        inputBinding.deleteIcon.setOnClickListener {
            inputBindings!!.remove(inputBinding)
            binding.playersScrollView.removeView(inputBinding.root)
            Toast.makeText(context, "DENISA", Toast.LENGTH_SHORT).show()
        }

        inputBinding.callback = object : Callback {
            override fun toggleFemale() {
                inputBinding.maleIcon.setImageResource(
                    R.drawable.boy_2
                )
                inputBinding.femaleIcon.setImageResource(
                    R.drawable.girl
                )
            }

            override fun toggleMale() {
                inputBinding.maleIcon.setImageResource(
                    R.drawable.boy
                )
                inputBinding.femaleIcon.setImageResource(
                    R.drawable.girl_2
                )
            }

            override fun expandAddPlayer() {
                inputBindings!!.remove(inputBinding)
                binding.playersScrollView.removeView(inputBinding.root)

            }
        }

        binding.addPlayerContainer.container1.apply {
            alpha = 1f
            visibility = View.VISIBLE
            animate()
                .alpha(0f)
                .setDuration(200)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        binding.addPlayerContainer.container1.visibility = View.GONE
                    }
                })
        }
        binding.addPlayerContainer.playerField.playerFieldContainer.animate().translationY(
            2f
        )
    }

    private fun expandAddPlayerView() {
        binding.addPlayerContainer.container1.apply {
            alpha = 0f
            visibility = View.VISIBLE
            animate()
                .alpha(1f)
                .setDuration(500)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        binding.addPlayerContainer.container1.visibility = View.VISIBLE
                    }
                })
        }
    }

    interface Callback {
        fun toggleFemale() {}
        fun toggleMale() {}
        fun startGame() {}
        fun addPlayer() {}
        fun expandAddPlayer() {}
    }

    open class ClickHandler {
        open fun handleQuestionClick(isTruth: Boolean) {}
        open fun handlePlayersClick() {}
        open fun handleLanguageClick() {}
        open fun handleNextPlayerClick() {}
    }
}