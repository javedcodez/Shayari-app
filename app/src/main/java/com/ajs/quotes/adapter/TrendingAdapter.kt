package com.ajs.quotes.adapter

import android.content.Context
import android.content.Intent
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
import com.ajs.quotes.ViewShayriActivity
import com.ajs.quotes.data.CategoryData

class TrendingAdapter(private val context: Context) : ListAdapter<CategoryData, TrendingAdapter.ViewHolder>(dif()){

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        fun bind(item:CategoryData){
            itemView.findViewById<TextView>(R.id.shayri).text = "\" ${item.shayri} \""
            itemView.findViewById<TextView>(R.id.author).text = " -- "+item.author

            itemView.findViewById<LinearLayout>(R.id.back).setOnClickListener {
                val intent = Intent(context, ViewShayriActivity::class.java)
                intent.putExtra("shayri",item.shayri)
                intent.putExtra("author",item.author)
                intent.putExtra("img",item.img)
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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_trending,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}