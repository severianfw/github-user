package com.severianfw.githubuser.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.severianfw.githubuser.adapter.FavoriteUserListAdapter
import com.severianfw.githubuser.database.FavoriteUser
import com.severianfw.githubuser.databinding.ActivityFavoriteBinding
import com.severianfw.githubuser.viewmodel.FavoriteUserViewModel

class FavoriteActivity : AppCompatActivity() {

    private lateinit var favoriteBinding: ActivityFavoriteBinding
    private var favUserAdapter: FavoriteUserListAdapter? = null
    private var favUserList: List<FavoriteUser>? = null
    private var favoriteUserViewModel: FavoriteUserViewModel? = null

    companion object {
        const val EXTRA_FAV_USER = "extra_fav_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        favoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(favoriteBinding.root)

        supportActionBar?.apply {
            title = "Favorite Users"
            setDisplayHomeAsUpEnabled(true)
        }

        favoriteUserViewModel = FavoriteUserViewModel(application)

        favoriteBinding.rvFavUser.layoutManager = LinearLayoutManager(this)

        favoriteUserViewModel?.getAllFavUser()?.observe(this) {
            favUserList = it
            favUserAdapter = FavoriteUserListAdapter(it)
            favUserAdapter?.setIconClickListener(object :
                FavoriteUserListAdapter.OnItemClickListener {
                override fun onDeleteClick(position: Int) {
                    if (favUserList != null) {
                        val username = favUserList!![position].login
                        showAlertDeleteDialog(username, position)
                    }
                }
            })
            favUserAdapter?.setOnItemClickCallback(object : FavoriteUserListAdapter.OnItemClickCallback{
                override fun onItemClicked(favUser: FavoriteUser) {
                    val detailIntent = Intent(this@FavoriteActivity, DetailActivity::class.java)
                    detailIntent.putExtra(DetailActivity.EXTRA_FAV_USER, favUser)
                    startActivity(detailIntent)
                }
            })
            favoriteBinding.rvFavUser.adapter = favUserAdapter
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showAlertDeleteDialog(username: String, position: Int) {
        val dialogTitle = "Remove User"
        val dialogMessage = "Are you sure want to remove $username from favorite?"

        val alertDialogBuilder = AlertDialog.Builder(this)
        with(alertDialogBuilder) {
            setTitle(dialogTitle)
            setMessage(dialogMessage)
            setCancelable(false)
            setPositiveButton("Yes") { _, _ ->
                favoriteUserViewModel?.delete(favUserList!![position])
                showToast("$username removed!")
            }
            setNegativeButton("No") { dialog, _ -> dialog.cancel() }
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}