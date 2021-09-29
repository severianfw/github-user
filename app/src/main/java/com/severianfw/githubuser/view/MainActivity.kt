package com.severianfw.githubuser.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.severianfw.githubuser.adapter.UserListAdapter
import com.severianfw.githubuser.databinding.ActivityMainBinding
import com.severianfw.githubuser.model.UserItem
import com.severianfw.githubuser.viewmodel.UserViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    private val userViewModel by viewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        supportActionBar?.title = "Github User Search"

        mainBinding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return if (query != null) {
                    userViewModel.getUserList(query)
                    true
                } else {
                    false
                }
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        mainBinding.rvUser.layoutManager = LinearLayoutManager(this)

        userViewModel.userList.observe(this, {
            setUsers(it)
        })

        userViewModel.isLoading.observe(this, {
            showProgressBar(it)
        })
    }

    private fun setUsers(userList: List<UserItem>) {
        val userListAdapter = UserListAdapter(userList)
        mainBinding.rvUser.adapter = userListAdapter

        userListAdapter.setOnItemClickCallback(object : UserListAdapter.OnItemClickCallback {
            override fun onItemClicked(user: UserItem) {
                val detailIntent = Intent(this@MainActivity, DetailActivity::class.java)
                detailIntent.putExtra(DetailActivity.EXTRA_USER, user)
                startActivity(detailIntent)
            }
        })
    }

    private fun showProgressBar(isLoading: Boolean) {
        if (isLoading) {
            mainBinding.progressBar.visibility = View.VISIBLE
        } else {
            mainBinding.progressBar.visibility = View.GONE
        }
    }
}