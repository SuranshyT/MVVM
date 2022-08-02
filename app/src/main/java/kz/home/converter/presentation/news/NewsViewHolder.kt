package kz.home.converter.presentation.news

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kz.home.converter.R
import kz.home.converter.domain.News

class NewsViewHolder(inflater: LayoutInflater, parent: ViewGroup, val onClick: (News) -> Unit) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.item_news, parent, false)) {

    private val newsTitle = itemView.findViewById<TextView>(R.id.item_news_title)
    private val newsImg = itemView.findViewById<ImageView>(R.id.item_news_img)

    fun bind(item: News) {
        newsTitle.text = item.title
        Glide.with(itemView.context).load(item.img).centerCrop().into(newsImg)

        newsImg.setOnClickListener {
            onClick(item)
        }
    }
}