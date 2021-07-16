package com.vironit.krasnovskaya_l23_p3.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vironit.domain.database.model.SearchEntity
import com.vironit.krasnovskaya_l23_p3.adapter.SearchAdapter
import com.vironit.krasnovskaya_l23_p3.databinding.FragmentFavouritesSearchBinding
import com.vironit.krasnovskaya_l23_p3.viewmodel.FavouriteSearchViewModel

class FavouritesSearchFragment : Fragment(), SearchAdapter.OnItemClickListener {

    private lateinit var binding: FragmentFavouritesSearchBinding
    private lateinit var adapter: SearchAdapter
    private val searchList = ArrayList<SearchEntity>()
    private val viewModelFavourite: FavouriteSearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouritesSearchBinding.inflate(layoutInflater)
        initRecyclerview()
        setObserver()
        viewModelFavourite.getSearches()
        return binding.root
    }

    private fun initRecyclerview() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = SearchAdapter(this)
        adapter.submitList(searchList)
        binding.recyclerView.adapter = adapter
    }

    private fun setObserver() {
        viewModelFavourite.searchList.observe(viewLifecycleOwner, { searches ->
            searchList.clear()
            searchList.addAll(searches)
            adapter.notifyDataSetChanged()
        })
    }

    override fun onDeleteClick(searchId: Int, position: Int) {
        searchList.removeAt(position)
        adapter.notifyDataSetChanged()
        viewModelFavourite.deleteSearch(searchId)
        setObserver()
    }

    override fun onItemClick(query: String) {
        val action =
            FavouritesFragmentDirections.actionFavouritesFragmentToSearchFragment(query)
        findNavController().navigate(action)
    }
}