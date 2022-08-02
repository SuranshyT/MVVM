package kz.home.converter.presentation.converter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_search_currency.view.*
import kz.home.converter.R
import kz.home.converter.domain.Currency

class AddBottomViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.item_search_currency, parent, false)) {
    private val flagImageView = itemView.flagImageView
    private val nameTextView = itemView.currencyTextView

    fun bind(item: Currency, clickListener: (name: Currency) -> Unit) {
        nameTextView.text = item.name
        Glide.with(itemView.context).load(item.img).centerCrop().into(flagImageView)

        nameTextView.setOnClickListener {
            clickListener(item)
        }
    }
}