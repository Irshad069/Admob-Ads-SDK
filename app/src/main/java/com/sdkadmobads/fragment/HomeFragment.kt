package com.sdkadmobads.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sdkadmobads.R
import com.sdkadmobads.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            toolBar.tvTitle.text = getString(R.string.home)
            btnShowBanner.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_bannerFragment)
            }
            btnShowInterstitial.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_interstitialFragment)
            }
            btnShowAdaptiveBanner.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_adaptiveBannerFragment)
            }
            btnShowRewardInterstitial.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_rewardInterstitialFragment)
            }
            btnShowReward.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_rewardFragment)
            }
            btnShowNative.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_nativeFragment)
            }
            btnShowRecyclerNative.setOnClickListener{
                findNavController().navigate(R.id.action_homeFragment_to_recyclerViewNativeFragment)
            }
            btnShowCollapsibleBanner.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_collapsibleBannerFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}