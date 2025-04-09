package com.sdkadmobads.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sdkadmobads.R
import com.sdkadmobads.data.MyItemModel
import com.sdkadmobads.databinding.FragmentRecyclerViewNativeBinding
import com.sdkadmobads.utils.ViewHolder
import com.sdkads.nativeadadapter.NativeAdAdapter

class RecyclerViewNativeFragment : Fragment() {

    private var _binding: FragmentRecyclerViewNativeBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: NativeAdAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecyclerViewNativeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolBar.tvTitle.text = getString(R.string.showing_recyclerview_ad)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val contentList = mutableListOf<Any>(
            MyItemModel(1, "The only limit to our realization of tomorrow is our doubts of today."),
            MyItemModel(2, "In the end, we will remember not the words of our enemies, but the silence of our friends."),
            MyItemModel(3, "It does not matter how slowly you go as long as you do not stop."),
            MyItemModel(4, "Success is not final, failure is not fatal: It is the courage to continue that counts."),
            MyItemModel(5, "You only live once, but if you do it right, once is enough."),
            MyItemModel(6, "The way to get started is to quit talking and begin doing."),
            MyItemModel(7, "Don't watch the clock; do what it does. Keep going."),
            MyItemModel(8, "Everything you can imagine is real."),
            MyItemModel(9, "The purpose of life is not to be happy. It is to be useful, to be honorable, to be compassionate, to have it make some difference that you have lived and lived well."),
            MyItemModel(10, "Life is 10% what happens to us and 90% how we react to it.")
        )

        adapter = NativeAdAdapter(
            requireContext(),
            contentList,
            5
        ) { parent ->
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
            ViewHolder(itemView)
        }

        binding.recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}