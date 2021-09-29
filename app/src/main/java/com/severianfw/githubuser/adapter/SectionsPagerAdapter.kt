package com.severianfw.githubuser.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.severianfw.githubuser.view.DetailActivity
import com.severianfw.githubuser.view.FollowerFragment
import com.severianfw.githubuser.view.FollowingFragment

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowerFragment()

            1 -> fragment = FollowingFragment()
        }
        // Send data to Fragment
        val bundle: Bundle = Bundle()
        bundle.putString("username", DetailActivity.USERNAME)
        fragment?.arguments = bundle

        return fragment as Fragment
    }
}