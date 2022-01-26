package com.denisatrif.truthdare.screens

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.denisatrif.truthdare.R
import com.denisatrif.truthdare.databinding.FragmentEntryScreenBinding
import com.denisatrif.truthdare.db.AppDatabase
import com.denisatrif.truthdare.db.repos.TruthDareRepository
import com.denisatrif.truthdare.viewmodel.TruthDaresViewModel
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class EntryScreenFragment : Fragment() {

    private var truthDaresViewModel: TruthDaresViewModel? = null
    private var _binding: FragmentEntryScreenBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        truthDaresViewModel = TruthDaresViewModel(
            TruthDareRepository(AppDatabase.getInstance(context).truthDareDao())
        )
        _binding = FragmentEntryScreenBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ObjectAnimator.ofFloat(binding.textviewFirst, "translationY", -400f).apply {
            duration = 1000
            start()
        }
        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_PlayersFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}