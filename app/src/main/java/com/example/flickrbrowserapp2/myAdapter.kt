package com.example.flickrbrowserapp2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.item_row.view.*

class myAdapter(private val list:ArrayList<ArrayList<String>>,val activity: MainActivity): RecyclerView.Adapter<myAdapter.ItemViewHolder>() {
    class ItemViewHolder(itemView : View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_row,
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val id = list[position][0]
        val title = list[position][1]
        val secret = list[position][2]
        val server = list[position][3]
        val link="https://farm66.staticflickr.com/${server}/${id}_${secret}.jpg"
        holder.itemView.apply {
            textView.text= title
            Glide.with(this)
                .load(link)
                .centerCrop()
                .into(imageView)
          imageView.setOnClickListener{
              Glide.with(activity)
                  .load(link)
                  .centerCrop()
                  .into(activity.imageView2)
              activity.imageView2.isVisible=true
          }
            activity.imageView2.setOnClickListener {
                activity.imageView2.isVisible=false
            }
        }
    }

    override fun getItemCount()=list.size
}