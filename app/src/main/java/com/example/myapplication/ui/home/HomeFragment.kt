package com.example.myapplication.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.databinding.FragmentHomeBinding
import okhttp3.internal.notifyAll
import org.json.JSONArray

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    data class FoodGroup(
        val name: String,
        val image: String,
        val introduction: String,
        val all: String
    )

    lateinit var adapter: GroupAdapter
    lateinit var groupListView: ListView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProviders.of(requireActivity()).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        groupListView = binding.groupListView
        homeViewModel.text2.observe(viewLifecycleOwner) {
            val jsonarray = JSONArray(it)
            var groups = List(jsonarray.length()) { num ->
                FoodGroup(jsonarray.getJSONObject(num).getString("name"),
                    if(jsonarray.getJSONObject(num).getJSONArray("images").length()!=0){
                        jsonarray.getJSONObject(num).getJSONArray("images").getJSONObject(0).getString("src")
                    }else{
                        "none"
                    },
                    jsonarray.getJSONObject(num).getString("introduction"),
                    jsonarray.getJSONObject(num).toString()
                )
            }

            adapter = GroupAdapter(requireContext(), groups,requireActivity())
            groupListView.adapter = adapter
            adapter.notifyDataSetChanged()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}