package com.ajs.quotes.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ajs.quotes.R
import com.ajs.quotes.ShayriListActivity
import com.ajs.quotes.data.CategoryData
import com.bumptech.glide.Glide
import java.io.Serializable

class AuthorAdapter(private val context: Context,private val list : List<CategoryData>) : ListAdapter<CategoryData, AuthorAdapter.ViewHolder>(dif()){

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        fun bind(item:CategoryData){
            val img = itemView.findViewById<ImageView>(R.id.profile_image)
            itemView.findViewById<TextView>(R.id.author_name).text = item.author

            Glide.with(context)
                .load(item.img)
                .into(img)


            itemView.findViewById<LinearLayout>(R.id.base).setOnClickListener {
                val intent = Intent(context, ShayriListActivity::class.java)
                intent.putExtra("key",item.author)
                intent.putExtra("isAuthor",true)
                intent.putExtra("data",list as Serializable)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            }

        }
    }

    private class dif() : DiffUtil.ItemCallback<CategoryData>(){
        override fun areItemsTheSame(oldItem: CategoryData, newItem: CategoryData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CategoryData, newItem: CategoryData): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_author,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}