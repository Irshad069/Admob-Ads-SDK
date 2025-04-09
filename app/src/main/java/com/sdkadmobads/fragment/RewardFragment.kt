package com.sdkadmobads.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sdkadmobads.R
import com.sdkadmobads.databinding.FragmentRewardBinding
import com.sdkads.reward.RewardedAdHelper

class RewardFragment : Fragment() {

    private var _binding: FragmentRewardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRewardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            toolBar.tvTitle.text = getString(R.string.showing_reward_ad)
            btnShowReward.setOnClickListener {
                RewardedAdHelper.showAd(
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