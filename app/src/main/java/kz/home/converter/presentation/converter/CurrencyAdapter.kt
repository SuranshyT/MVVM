package kz.home.converter.presentation.converter

import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_choose_currency.view.*
import kz.home.converter.domain.Currency

class CurrencyAdapter(
    private val itemTouchDelegate: ItemTouchDelegate,
    private val handlingLongClick: (Int) -> Unit
) : RecyclerView.Adapter<CurrencyViewHolder>() {

    private var data = mutableListOf<Currency>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewHolder = CurrencyViewHolder(inflater, parent)

        viewHolder.itemView.card_view.setOnTouchListener { _, event ->
            if (event.actionMasked == MotionEvent.ACTION_DOWN) {
                itemTouchDelegate.startDragging(viewHolder)
            }
            return@setOnTouchListener true
        }

        viewHolder.itemView.currencyTextView.setOnLongClickListener() {
            handlingLongClick(viewHolder.adapterPosition)
            return@setOnLongClickListener true
        }
        return viewHolder
    }


    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    fun addItem(item: Currency) {
        data.add(item)
        notifyDataSetChanged()
    }

    fun addItemAt(index: Int, item: Currency) {
        data.add(index, item)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }

    fun setItems(list: MutableList<Currency>) {
        data.clear()
        data.addAll(list)
        for(item in list) {
            Log.e("Tag", "$item")
        }
        notifyDataSetChanged()
    }

    fun moveItem(from: Int, to: Int) {
        val fromData = data[from]
        data.removeAt(from)
        if (to < from) {
            data.add(to, fromData)
        } else {
            data.add(to - 1, fromData)
        }
    }
}