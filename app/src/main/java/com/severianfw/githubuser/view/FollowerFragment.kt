package com.severianfw.githubuser.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.severianfw.githubuser.R
import com.severianfw.githubuser.adapter.FollowListAdapter
import com.severianfw.githubuser.viewmodel.UserViewModel

class FollowerFragment : Fragment() {

    private val userViewModel by activityViewModels<UserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_follower, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString("username", "")
        if (username != null) {
            userViewModel.getFollowers(username)
        }

        val rvFollower: RecyclerView = view.findViewById(R.id.rv_follower)

        rvFollower.layoutManager = LinearLayoutManager(view.context)

        userViewModel.followerList.observe(viewLifecycleOwner, {
            rvFollower.adapter = FollowListAdapter(it)
        })

        userViewModel.isTabLoading.observe(viewLifecycleOwner, {
            val progressBar: ProgressBar = view.findViewById(R.id.progress_bar)
            if (it) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        })

    }

}