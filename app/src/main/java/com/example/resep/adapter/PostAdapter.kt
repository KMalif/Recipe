package com.example.resep.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.resep.RecipeActivity
import com.example.resep.databinding.ListItemPostBinding
import com.example.resep.models.Post


class PostAdapter(private val posts: List<Post>, private val listener : PostAdapter.OnAdapterListener): RecyclerView.Adapter<PostAdapter.viewHolder>(){
    inner class viewHolder(val binding: ListItemPostBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(ListItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.binding.apply {
            postTitle.text =posts[position].title
            postDetail.text = posts[position].content
        }
        holder.itemView.setOnClickListener {
            listener.Onclick(posts[position])
        }
    }

    interface OnAdapterListener{
        fun Onclick(post : Post)
    }
}