package com.severianfw.githubuser.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.severianfw.githubuser.R
import com.severianfw.githubuser.adapter.SectionsPagerAdapter
import com.severianfw.githubuser.database.FavoriteUser
import com.severianfw.githubuser.databinding.ActivityDetailBinding
import com.severianfw.githubuser.model.UserDetailResponse
import com.severianfw.githubuser.model.UserItem
import com.severianfw.githubuser.viewmodel.FavoriteUserViewModel
import com.severianfw.githubuser.viewmodel.UserViewModel

class DetailActivity : AppCompatActivity() {
    private lateinit var detailBinding: ActivityDetailBinding
    private val userViewModel by viewModels<UserViewModel>()

    companion object {
        const val EXTRA_USER = "extra_user"
        const val EXTRA_FAV_USER = "extra_fav_user"
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
            setDisplayHomeAsUpEnabled(true)
        }

        val user = intent.getParcelableExtra<UserItem>(EXTRA_USER)
        val favUser = intent.getParcelableExtra<FavoriteUser>(EXTRA_FAV_USER)
        if (user != null) {
            userViewModel.getUserDetails(user.login)
            USERNAME = user.login
        } else if (favUser != null) {
            userViewModel.getUserDetails(favUser.login)
            USERNAME = favUser.login
        }

        userViewModel.user.observe(this, { userDetail ->
            setUserData(userDetail)

            detailBinding.btnAddFav.setOnClickListener {
                val favoriteUserViewModel = FavoriteUserViewModel(application)
                    val favUser = FavoriteUser(
                        id = userDetail.id,
                        avatarUrl = userDetail.avatarUrl,
                        htmlUrl = userDetail.htmlUrl,
                        login = userDetail.login,
                        name = userDetail.name,
                        repository = userDetail.publicRepos,
                        following = userDetail.following,
                        followers = userDetail.followers
                    )
                val result = favoriteUserViewModel.insert(favUser)

                if (result.equals(5)) {
                    showToast("${userDetail.login} already added!")
                } else {
                    showToast("${userDetail.login} added to favorite!")
                }
                val favIntent = Intent(this, FavoriteActivity::class.java)
                favIntent.putExtra(FavoriteActivity.EXTRA_FAV_USER, favUser)
            }
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)

        val item = menu?.findItem(R.id.ic_favourite)
        item?.isVisible = false

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.ic_settings -> startActivity(Intent(this, SettingsActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}