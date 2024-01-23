package com.example.myapplication.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.databinding.FragmentDashboardBinding
import org.json.JSONObject

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    data class FoodGroup(
        val name: String,
        val image: String,
    )
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var adapter: GroupAdapter
    lateinit var groupListView: ListView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel = ViewModelProviders.of(requireActivity()).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        groupListView = binding.groupListView
        groupListView.dividerHeight = 0
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            val jsonarray = JSONObject(it)
            val groups = List(jsonarray.getJSONArray("images").length()) { num ->
                if(num<jsonarray.getJSONArray("images").length()){
                    FoodGroup(
                        "image",
                        jsonarray.getJSONArray("images").getJSONObject(num)
                            .getString("src")
                    )
                }else{
                    FoodGroup(
                        "string",
                        jsonarray.getString("introduction")
                    )
                }
            }.toMutableList()
            groups+=FoodGroup(
                "introduction",
                jsonarray.getString("introduction")
            )
            groups+=FoodGroup(
                "official_site",
                jsonarray.getString("official_site")
            )
            adapter = GroupAdapter(requireContext(), groups,requireActivity())
            groupListView.adapter = adapter
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}