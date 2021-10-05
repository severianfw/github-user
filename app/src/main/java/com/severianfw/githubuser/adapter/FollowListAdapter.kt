package com.severianfw.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.severianfw.githubuser.databinding.ItemUserBinding
import com.severianfw.githubuser.model.UserItem

class FollowListAdapter(private val listUser: List<UserItem>) :
    RecyclerView.Adapter<FollowListAdapter.ListViewHolder>() {

    inner class ListViewHolder(private val itemUserBinding: ItemUserBinding) : RecyclerView.ViewHolder(itemUserBinding.root) {

        fun bind(user: UserItem) {
            Glide.with(itemUserBinding.root)
                .load(user.avatarUrl)
                .into(itemUserBinding.ivAvatar)

            itemUserBinding.apply {
                tvUsername.text = user.login
                tvHtmlUrl.text = user.htmlUrl
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val itemUserBinding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(itemUserBinding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listUser[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int = listUser.size

}