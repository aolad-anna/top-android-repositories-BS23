package com.example.top_android_repositoriesbs_23.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.top_android_repositoriesbs_23.R
import com.example.top_android_repositoriesbs_23.network.Repository

class RepositoriesAdapter: ListAdapter<Repository, RepositoriesAdapter.ViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Repository>() {
        override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean {
            return oldItem == newItem
        }
    }

    var onItemClicked: (Repository) -> Unit = {}

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val avatarImageView: ImageView = itemView.findViewById(R.id.userAvatar)
        val ropoNameTextView: TextView = itemView.findViewById(R.id.repoName)
        val userNameTextView: TextView = itemView.findViewById(R.id.userName)
    }

    /**
     * Create new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.repository_recyclerview_item, parent, false)
        return ViewHolder(view)
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = currentList[position]
        holder.avatarImageView.load(item.owner.avatar)
        holder.ropoNameTextView.text = item.name
        holder.userNameTextView.text = item.owner.login
        holder.itemView.setOnClickListener {
            onItemClicked(currentList[position])
        }
    }
}