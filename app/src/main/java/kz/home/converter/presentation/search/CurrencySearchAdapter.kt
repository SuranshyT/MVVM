package kz.home.converter.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kz.home.converter.domain.Currency

class CurrencySearchAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var data = mutableListOf<Currency>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return CurrencySearchViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CurrencySearchViewHolder).bind(data[position])
    }

    fun setItems(list: MutableList<Currency>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }
}