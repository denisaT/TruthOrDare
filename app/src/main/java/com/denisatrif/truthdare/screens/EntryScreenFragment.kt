package com.denisatrif.truthdare.screens

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.GrayscaleTransformation
import com.denisatrif.truthdare.R
import com.denisatrif.truthdare.databinding.FragmentEntryScreenBinding
import com.denisatrif.truthdare.viewmodel.TruthDaresViewModel
import com.denisatrif.truthdare.viewmodel.TruthDaresViewModelFactory


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class EntryScreenFragment : Fragment() {

    private lateinit var truthDaresViewModel: TruthDaresViewModel
    private var _binding: FragmentEntryScreenBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        truthDaresViewModel = ViewModelProvider(
            this,
            TruthDaresViewModelFactory()
        )[TruthDaresViewModel::class.java]
        _binding = FragmentEntryScreenBinding.inflate(inflater, container, false)
            .apply {
                lifecycleOwner = viewLifecycleOwner
            }
        return binding.root
    }

    override fun onResume() {
        truthDaresViewModel.getUnsplashPhotos().observe(binding.lifecycleOwner!!) {
            println(it.urls.raw)
//            binding.backgroundImage.load(it.urls.raw) {
//                transformations(
//                    GrayscaleTransformation(),
//                )
//                build()
//            }
        }

        super.onResume()
        binding.circularProgressIndicator.show()
        val interval = 2000L
        val handler = Handler(Looper.getMainLooper())
        val runnable =
            Runnable {
                binding.circularProgressIndicator.hide()
                findNavController().navigate(R.id.action_FirstFragment_to_PlayersFragment)
            }
        handler.postAtTime(runnable, System.currentTimeMillis() + interval);
        handler.postDelayed(runnable, interval)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}