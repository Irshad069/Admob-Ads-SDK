package com.sdkadmobads.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sdkadmobads.R
import com.sdkadmobads.databinding.FragmentCollapsibleBannerBinding

class CollapsibleBannerFragment : Fragment() {

    private var _binding: FragmentCollapsibleBannerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCollapsibleBannerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolBar.tvTitle.text = getString(R.string.showing_collapsible_banner)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}