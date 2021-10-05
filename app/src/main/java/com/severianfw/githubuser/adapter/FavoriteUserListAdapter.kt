package com.severianfw.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.severianfw.githubuser.database.FavoriteUser
import com.severianfw.githubuser.databinding.ItemFavUserBinding

class FavoriteUserListAdapter(private val favUserList: List<FavoriteUser>) :
    RecyclerView.Adapter<FavoriteUserListAdapter.ListViewHolder>() {

    private lateinit var mListener: OnItemClickListener
    private lateinit var onItemClickCallback: OnItemClickCallback


    inner class ListViewHolder(
        private val itemFavUserBinding: ItemFavUserBinding
    ) :
        RecyclerView.ViewHolder(itemFavUserBinding.root) {

        private val icDelete = itemFavUserBinding.icDelete

        fun bind(favoriteUser: FavoriteUser) {
            Glide.with(itemFavUserBinding.root)
                .load(favoriteUser.avatarUrl)
                .into(itemFavUserBinding.ivAvatar)

            itemFavUserBinding.apply {
                tvUsername.text = favoriteUser.login
                tvFollowerNum.text = favoriteUser.followers.toString()
                tvRepositoryNum.text = favoriteUser.repository.toString()
            }

            icDelete.setOnClickListener {
                val position: Int = absoluteAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onDeleteClick(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteUserListAdapter.ListViewHolder {
        val itemFavUserBinding =
            ItemFavUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(itemFavUserBinding)
    }

    override fun onBindViewHolder(holder: FavoriteUserListAdapter.ListViewHolder, position: Int) {
        val user = favUserList[position]
        holder.bind(user)

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(favUserList[holder.absoluteAdapterPosition])
        }
    }

    override fun getItemCount(): Int = favUserList.size

    fun setIconClickListener(onItemClickListener: OnItemClickListener) {
        mListener = onItemClickListener
    }

    interface OnItemClickListener {
        fun onDeleteClick(position: Int)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(favUser: FavoriteUser)
    }

}