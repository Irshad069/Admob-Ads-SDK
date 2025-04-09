package com.sdkadmobads.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sdkadmobads.R
import com.sdkadmobads.databinding.FragmentRewardInterstitialBinding
import com.sdkads.rewardinterstitial.RewardedInterstitialHelper

class RewardInterstitialFragment : Fragment() {

    private var _binding: FragmentRewardInterstitialBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRewardInterstitialBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            toolBar.tvTitle.text = getString(R.string.showing_reward_interstitial_ad)
            btnShowRewardInterstitial.setOnClickListener {
                RewardedInterstitialHelper.showAd(
                    activity = requireActivity(),
                    onAdClosed = {
                        Log.d("AdDemo", "Ad was dismissed.")
                    },
                    onUserEarnedReward = { rewardAmount, rewardType ->
                        Log.d("AdDemo", "User earned $rewardAmount $rewardType.")
                    }
                )
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}