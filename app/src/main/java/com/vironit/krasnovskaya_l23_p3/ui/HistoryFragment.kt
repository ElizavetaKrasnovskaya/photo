package com.vironit.krasnovskaya_l23_p3.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vironit.domain.database.model.SearchEntity
import com.vironit.krasnovskaya_l23_p3.adapter.HistoryAdapter
import com.vironit.krasnovskaya_l23_p3.databinding.FragmentHistoryBinding
import com.vironit.krasnovskaya_l23_p3.viewmodel.SearchViewModel

class HistoryFragment : Fragment(), HistoryAdapter.OnItemClickListener {

    private lateinit var binding: FragmentHistoryBinding
    private lateinit var adapter: HistoryAdapter
    private val searchList = ArrayList<SearchEntity>()
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(layoutInflater)
        initRecyclerview()
        setObserver()
        viewModel.getSearches()
        return binding.root
    }

    private fun initRecyclerview() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = HistoryAdapter(this)
        adapter.submitList(searchList)
        binding.recyclerView.adapter = adapter
    }

    private fun setObserver() {
        viewModel.searchList.observe(viewLifecycleOwner, { searches ->
            searchList.clear()
            searchList.addAll(searches)
            adapter.notifyDataSetChanged()
        })
    }

    override fun onItemClick(query: String) {
        val action = HistoryFragmentDirections.actionHistoryFragmentToSearchFragment(query)
        findNavController().navigate(action)
    }

    override fun onLikeClick(id: Int, isFavourite: Int) {
        viewModel.addToFavourites(id, isFavourite)
    }
}