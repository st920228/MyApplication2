package com.example.myapplication.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import coil.load
import com.example.myapplication.R

class GroupAdapter(
    var context: Context,
    var groups: List<HomeFragment.FoodGroup>,
    var requireActivity: FragmentActivity
): BaseAdapter(){
    override fun getCount(): Int {
        return groups.count()
    }

    override fun getItem(p0: Int): HomeFragment.FoodGroup {
        return groups[p0]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val groupView = LayoutInflater
            .from(context)
            .inflate(R.layout.group_list_item, null)

        var groupImage: ImageView = groupView.findViewById(R.id.groupImage)
        var groupText: TextView = groupView.findViewById(R.id.groupName)
        var introduction: TextView = groupView.findViewById(R.id.introduction)

        var group = getItem(p0)

        groupImage.load(group.image){
            error(com.google.android.material.R.drawable.mtrl_ic_error)
        }

        groupText.text = group.name
        introduction.text = group.introduction

        groupView.setOnClickListener {
            requireActivity.run {
                ViewModelProviders.of(this).get(HomeViewModel::class.java).setNext(group.all)
            }
        }

        return groupView
    }
}