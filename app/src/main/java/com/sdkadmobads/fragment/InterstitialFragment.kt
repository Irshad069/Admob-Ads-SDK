package com.sdkadmobads.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sdkadmobads.R
import com.sdkadmobads.databinding.FragmentInterstitialBinding
import com.sdkads.interstitial.InterstitialHelper

class InterstitialFragment : Fragment() {

    private var _binding: FragmentInterstitialBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInterstitialBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            toolBar.tvTitle.text = getString(R.string.showing_interstitial_ad)
            btnShowInterstitial.setOnClickListener {
                InterstitialHelper.showAd(requireActivity()) {
                    Log.d("TAG", "onViewCreated: Ad closed. Proceeding...")
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}