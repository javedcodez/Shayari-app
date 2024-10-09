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
import com.ajs.quotes.data.CategoryData
import com.bumptech.glide.Glide
import java.io.Serializable

class CategoriesAdapter(private val context: Context, private val list : List<CategoryData>) : ListAdapter<CategoryData, CategoriesAdapter.ViewHolder>(dif()){

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        fun bind(item:CategoryData){

            itemView.findViewById<TextView>(R.id.category_name).text = "# "+item.category

            itemView.findViewById<LinearLayout>(R.id.base).setOnClickListener {
                val intent = Intent(context,ShayriListActivity::class.java)
                intent.putExtra("key",item.category)
                intent.putExtra("isAuthor",false)
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
       val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category,parent,false)
       return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}