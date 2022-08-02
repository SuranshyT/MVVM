package kz.home.converter.presentation.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kz.home.converter.domain.News

class NewsAdapter(val onClick: (News) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val data = mutableListOf<News>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return NewsViewHolder(inflater, parent, onClick = { onClick(it) })
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as NewsViewHolder).bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    fun setItems(list: MutableList<News>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }
}