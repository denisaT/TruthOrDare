package com.denisatrif.truthdare.screens

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.denisatrif.truthdare.R
import com.denisatrif.truthdare.databinding.FragmentEntryScreenBinding
import com.denisatrif.truthdare.viewmodel.TruthDaresViewModel

class EntryScreenFragment : Fragment() {

    private val truthDaresViewModel: TruthDaresViewModel by viewModels()
    private var binding: FragmentEntryScreenBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentEntryScreenBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onResume() {
        truthDaresViewModel.getUnsplashPhotos().observe(viewLifecycleOwner) {
            println(it.urls.raw)
//            binding.backgroundImage.load(it.urls.raw) {
//                transformations(
//                    GrayscaleTransformation(),
//                )
//                build()
//            }
        }

        super.onResume()
        binding?.circularProgressIndicator?.show()
        val interval = 2000L
        val handler = Handler(Looper.getMainLooper())
        val runnable =
            Runnable {
                binding?.circularProgressIndicator?.hide()
                findNavController().navigate(R.id.action_FirstFragment_to_PlayersFragment)
            }
        handler.postAtTime(runnable, System.currentTimeMillis() + interval);
        handler.postDelayed(runnable, interval)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}