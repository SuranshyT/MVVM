package kz.home.converter.presentation.converter

import androidx.recyclerview.widget.RecyclerView

interface ItemTouchDelegate {
    fun startDragging(viewHolder: RecyclerView.ViewHolder)
}