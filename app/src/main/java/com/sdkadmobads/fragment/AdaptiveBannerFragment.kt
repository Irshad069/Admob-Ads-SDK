package com.sdkadmobads.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sdkadmobads.R
import com.sdkadmobads.databinding.FragmentAdaptiveBannerBinding

class AdaptiveBannerFragment : Fragment() {

    private var _binding: FragmentAdaptiveBannerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdaptiveBannerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolBar.tvTitle.text = getString(R.string.showing_adaptive_banner)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}