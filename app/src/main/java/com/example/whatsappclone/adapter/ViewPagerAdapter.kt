package com.example.whatsappclone.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter

class ViewPagerAdapter(
    private val context: Context,
    fm:FragmentManager?,
    private val list:ArrayList<Fragment>
) : FragmentPagerAdapter(fm!!){
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Fragment {
        return list[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }
    companion object Titles{
        val titleList = arrayOf("Chat","Updates","Call")
    }
}