package kz.home.converter.presentation.converter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_choose_currency.view.*
import kz.home.converter.R
import kz.home.converter.domain.Currency

class CurrencyViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.item_choose_currency, parent, false)) {
    private val flagImageView = itemView.flagImageView
    private val nameTextView = itemView.currencyTextView
    private val valueTextView = itemView.outlinedTextInput

    fun bind(item: Currency) {
        nameTextView.text = item.name
        valueTextView.text = item.value.toString()
        Glide.with(itemView.context).load(item.img).centerCrop().into(flagImageView)

    }
}