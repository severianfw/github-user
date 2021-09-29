package com.severianfw.githubuser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.severianfw.githubuser.R
import com.severianfw.githubuser.model.UserItem

class FollowListAdapter(private val listUser: List<UserItem>) :
    RecyclerView.Adapter<FollowListAdapter.ListViewHolder>() {

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvUsername: TextView = itemView.findViewById(R.id.tv_username)
        private val tvUrl: TextView = itemView.findViewById(R.id.tv_html_url)
        private val ivAvatar: ImageView = itemView.findViewById(R.id.iv_avatar)

        fun bind(user: UserItem) {
            Glide.with(itemView)
                .load(user.avatarUrl)
                .into(ivAvatar)

            tvUsername.text = user.login
            tvUrl.text = user.htmlUrl
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_row_user, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listUser[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int = listUser.size


}