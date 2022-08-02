package kz.home.converter.presentation.converter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kz.home.converter.R
import kz.home.converter.utils.allCurrenciesList
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AddBottomSheet(
    val scroll: () -> Unit
): BottomSheetDialogFragment() {

    private val viewModel: ConverterViewModel by sharedViewModel()
    private lateinit var recyclerView: RecyclerView
    private lateinit var addBottomAdapter: AddBottomAdapter
    private lateinit var manager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.add_bottom_sheet, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        setupRecyclerView(view)

        addBottomAdapter.setItems(allCurrenciesList)
    }

    private fun setupRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)

        addBottomAdapter = AddBottomAdapter(clickListener = {
            viewModel.addCurrency(it)
            viewModel.calculateConvertation()
            scroll()
            dismiss()
        })

        manager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        recyclerView.apply {
            adapter = addBottomAdapter
            layoutManager = manager
        }
        recyclerView.setHasFixedSize(true)
    }
}