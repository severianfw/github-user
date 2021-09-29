package com.severianfw.githubuser.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.severianfw.githubuser.R
import com.severianfw.githubuser.adapter.SectionsPagerAdapter
import com.severianfw.githubuser.databinding.ActivityDetailBinding
import com.severianfw.githubuser.model.UserDetailResponse
import com.severianfw.githubuser.model.UserItem
import com.severianfw.githubuser.viewmodel.UserViewModel

class DetailActivity : AppCompatActivity() {
    private lateinit var detailBinding: ActivityDetailBinding
    private val userViewModel by viewModels<UserViewModel>()

    companion object {
        const val EXTRA_USER = "extra_user"
        var USERNAME = ""

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follower,
            R.string.following
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)

        supportActionBar?.apply {
            title = "Detail User"
            elevation = 0f
        }

        val user = intent.getParcelableExtra<UserItem>(EXTRA_USER)
        if (user != null) {
            userViewModel.getUserDetails(user.login)
            USERNAME = user.login
        }

        userViewModel.user.observe(this, {
            setUserData(it)
        })

        userViewModel.isLoading.observe(this, {
            showProgressBar(it)
        })

        setTabLayout()
    }

    private fun setUserData(user: UserDetailResponse) {
        Glide.with(this@DetailActivity)
            .load(user.avatarUrl)
            .into(detailBinding.ivAvatarDetail)

        detailBinding.apply {
            tvName.text = user.name
            tvUsername.text = user.login
            tvFollowerNum.text = user.followers.toString()
            tvFollowingNum.text = user.following.toString()
            tvRepositoryNum.text = user.publicRepos.toString()
        }
    }

    private fun setTabLayout() {
        detailBinding.viewPager.adapter = SectionsPagerAdapter(this)
        TabLayoutMediator(detailBinding.tabs, detailBinding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun showProgressBar(isLoading: Boolean) {
        if (isLoading) {
            detailBinding.progressBar.visibility = View.VISIBLE
        } else {
            detailBinding.progressBar.visibility = View.GONE
        }
    }
}