package kz.home.converter.presentation.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.search_fragment.*
import kz.home.converter.*
import kz.home.converter.domain.Currency
import kz.home.converter.utils.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment(R.layout.search_fragment) {
    private val viewModel: SearchViewModel by viewModel()
    private lateinit var searchInput: TextInputEditText
    private lateinit var currencyAdapter: CurrencySearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchedList: MutableList<Currency> = mutableListOf()
        val chipGroup = view.findViewById<ChipGroup>(R.id.chipGroup)
        searchInput = view.findViewById(R.id.searchInput)

        recyclerViewSearch.visibility = View.GONE
        setupCurrencySearch(allCurrenciesList)

        handlingTyping(searchedList)
        handlingDoneButton(chipsList, chipGroup, view)

        addAllChips(chipsList, chipGroup)

        viewModel.chipLiveData.observe(viewLifecycleOwner) {

        }
    }

    private fun handlingTyping(searchedList: MutableList<Currency>) {
        searchInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val chip = searchInput.text.toString()
                if (chip.isNotBlank()) {
                    for (i in allCurrenciesList.indices) {
                        if (allCurrenciesList[i].name.lowercase().contains(chip.lowercase())) {
                            searchedList.add(allCurrenciesList[i])
                        }
                    }
                }
                if (searchedList.isEmpty()) {
                    recyclerViewSearch.visibility = View.GONE
                } else {
                    recyclerViewSearch.visibility = View.VISIBLE
                    currencyAdapter.setItems(searchedList)
                    searchedList.clear()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    private fun handlingDoneButton(
        chipsList: MutableList<String>,
        chipGroup: ChipGroup,
        view: View
    ) {
        searchInput.setOnEditorActionListener { _, keyCode, _ ->
            if (keyCode == EditorInfo.IME_ACTION_DONE) {
                val chip = searchInput.text.toString()
                if (chip.isNotBlank()) {
                    if (!chipsList.contains(chip)) {
                        chipsList.add(chip)
                        val index = chipsList.indexOf(chip)
                        addChip(chip, index, chipGroup)
                    }
                }
                view.hideKeyboard()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    private fun addChip(chipText: String, index: Int, chipGroup: ChipGroup) {
        (layoutInflater.inflate(R.layout.chip_choice, chipGroup, false) as? Chip)?.let { chip ->
            chip.id = index
            chip.text = chipText
            chip.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    searchInput.setText(chipText)
                }
            }
            chipGroup.addView(chip)
        }
    }

    fun addAllChips(chipList: MutableList<String>, chipGroup: ChipGroup) {
        for (i in chipList.indices) {
            addChip(chipList[i], i, chipGroup)
        }
    }

    private fun setupCurrencySearch(list: MutableList<Currency>) {
        currencyAdapter = CurrencySearchAdapter()
        currencyAdapter.setHasStableIds(true)
        val currencyManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        recyclerViewSearch.apply {
            adapter = currencyAdapter
            layoutManager = currencyManager
        }
        recyclerViewSearch.setHasFixedSize(true)

        currencyAdapter.setItems(list)
    }
}