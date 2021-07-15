package com.vironit.krasnovskaya_l23_p3.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vironit.data.database.model.SearchEntity
import com.vironit.krasnovskaya_l23_p3.adapter.HistoryAdapter
import com.vironit.krasnovskaya_l23_p3.databinding.FragmentHistoryBinding
import com.vironit.krasnovskaya_l23_p3.viewmodel.SearchViewModel

class HistoryFragment : Fragment(), HistoryAdapter.OnItemClickListener {

    private lateinit var binding: FragmentHistoryBinding
    private lateinit var adapter: HistoryAdapter
    private val searchList = ArrayList<SearchEntity>()
    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        initRecyclerview()
        setObserver()
        viewModel.getSearches(requireContext())
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
        viewModel.addToFavourites(requireContext(), id, isFavourite)
    }

//    override fun onDeleteClick(searchId: Int, position: Int) {
//        searchList.removeAt(position)
//        adapter.notifyDataSetChanged()
//        viewModel.addToFavourites(requireContext(), searchId)
//        setObserver()
//    }

//    override fun onItemClick(id: Int) {
//        val action =
//            FavouritesFragmentDirections.actionFavouritesFragmentToDetailsFragment(photoId)
//        findNavController().navigate(action)
//    }

}