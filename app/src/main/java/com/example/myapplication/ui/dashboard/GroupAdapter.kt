package com.example.myapplication.ui.dashboard

import android.content.Context
import android.graphics.Color
import android.text.SpannableString
import android.text.style.UnderlineSpan
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
import com.example.myapplication.ui.notifications.NotificationsViewModel


class GroupAdapter(
    var context: Context,
    var groups: List<DashboardFragment.FoodGroup>,
    var requireActivity: FragmentActivity
): BaseAdapter(){
    override fun getCount(): Int {
        return groups.count()
    }

    override fun getItem(p0: Int): DashboardFragment.FoodGroup {
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
        if(group.name=="image"){
            groupImage.load(group.image){
                error(com.google.android.material.R.drawable.mtrl_ic_error)
            }
            groupText.visibility = View.GONE
            introduction.visibility = View.GONE
        }else if(group.name=="introduction"){
            groupImage.visibility = View.GONE
            introduction.visibility = View.GONE
            groupText.text = group.image
        } else {
            groupImage.visibility = View.GONE
            introduction.visibility = View.GONE
            groupText.setTextColor(Color.parseColor("#0645ad"))
            val content = SpannableString(group.image)
            content.setSpan(UnderlineSpan(), 0, group.image.length, 0)
            groupText.text = content

        }

        groupView.setOnClickListener {
            requireActivity.run {
                ViewModelProviders.of(this).get(NotificationsViewModel::class.java).settext(group.image)
            }
        }

        return groupView
    }
}