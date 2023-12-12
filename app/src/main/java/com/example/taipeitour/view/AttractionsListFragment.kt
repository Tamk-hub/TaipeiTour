package com.example.taipeitour.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taipeitour.R
import com.example.taipeitour.databinding.FragmentAttractionsListBinding
import com.example.taipeitour.viewmodel.AttractionsListViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class AttractionsListFragment : Fragment() {

    private var _binding: FragmentAttractionsListBinding? = null


    private val binding get() = _binding!!
    private val viewModel: AttractionsListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAttractionsListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val recyclerView: RecyclerView = binding.recycleView
        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = AttractionsAdapter(emptyList())
        recyclerView.adapter = adapter

        viewModel.attractionsList.observe(viewLifecycleOwner, Observer { attractionsList ->
            adapter.updateData(attractionsList)
        })

        // 監聽語言變化
        (requireActivity() as MainActivity).viewModel.selectedLanguage.observe(viewLifecycleOwner, Observer { language ->
            // 語言變化時重新拉取數據
            viewModel.fetchData(language)
        })

        viewModel.fetchData((requireActivity() as MainActivity).viewModel.getCurrentLanguage())


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}