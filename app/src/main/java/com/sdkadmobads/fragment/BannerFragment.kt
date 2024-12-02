package com.sdkadmobads.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sdkadmobads.R
import com.sdkadmobads.databinding.FragmentBannerBinding

class BannerFragment : Fragment() {

    private var _binding: FragmentBannerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBannerBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolBar.tvTitle.text = getString(R.string.showing_banner_ad)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}