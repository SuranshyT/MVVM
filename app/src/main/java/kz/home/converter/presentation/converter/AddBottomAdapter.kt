package kz.home.converter.presentation.converter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kz.home.converter.domain.Currency

class AddBottomAdapter(
    private val clickListener: (name: Currency) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var data = mutableListOf<Currency>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return AddBottomViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as AddBottomViewHolder).bind(data[position], clickListener)
    }

    fun setItems(list: MutableList<Currency>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }
}