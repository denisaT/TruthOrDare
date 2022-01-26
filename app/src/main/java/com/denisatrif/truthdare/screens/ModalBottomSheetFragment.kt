package com.denisatrif.truthdare.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.denisatrif.truthdare.databinding.GameBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ModalBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: GameBottomSheetBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //_binding = GameBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val child = binding.standardBottomSheet
        val modalBottomSheetBehavior = BottomSheetBehavior.from(child)
        //modalBottomSheetBehavior.addBottomSheetCallback(bottomSheetCallback)
    }



    companion object {
        const val TAG = "ModalBottomSheet"
    }
}
