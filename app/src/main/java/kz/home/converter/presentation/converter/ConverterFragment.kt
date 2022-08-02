package kz.home.converter.presentation.converter

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import kz.home.converter.*
import kz.home.converter.domain.Currency
import kz.home.converter.utils.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import com.google.android.material.snackbar.Snackbar

class ConverterFragment(
    //private val updateConvertations: (Int) -> Unit
) :
    Fragment(R.layout.converter_fragment), ItemTouchDelegate {

    private lateinit var currencyAdapter: CurrencyAdapter
    private lateinit var currencyManager: LinearLayoutManager
    private lateinit var deleteDialog: DeleteItemDialogFragment
    private lateinit var snackbarView: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    private val viewModel: ConverterViewModel by sharedViewModel()
    private var position = addedCurrenciesList.size
    private var lastDeletedItem = Currency(randomID(), "", "", "", 0.0)
    private var lastDeletedPosition = 0
    private var unSortedNameList: MutableList<Currency> = mutableListOf()
    private var convertationCounter = 0
    private var chosenIndex = 2

    private val itemTouchHelper by lazy {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.START or ItemTouchHelper.END,
            ItemTouchHelper.LEFT
        ) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val currencyAdapter = recyclerView.adapter as CurrencyAdapter
                val from = viewHolder.adapterPosition
                val to = target.adapterPosition
                currencyAdapter.moveItem(from, to)
                currencyAdapter.notifyItemMoved(from, to)
                val fromData = addedCurrenciesList[from]
                addedCurrenciesList.removeAt(from)
                addedCurrenciesList.add(to, fromData)

                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val positionToRemove = viewHolder.adapterPosition
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        addedCurrenciesList.removeAt(positionToRemove)
                        currencyAdapter.removeItem(positionToRemove)
                    }
                }
            }

            override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
                super.onSelectedChanged(viewHolder, actionState)
                if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                    viewHolder?.itemView?.alpha = 0.5f
                }
            }

            override fun clearView(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ) {
                super.clearView(recyclerView, viewHolder)
                viewHolder.itemView.alpha = 1.0f
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerView)
        snackbarView = view.findViewById(R.id.mainLayout)
        toolbar = view.findViewById(R.id.toolbar)

        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        setHasOptionsMenu(true)

        setupCurrency(addedCurrenciesList)

        viewModel.list.observe(viewLifecycleOwner) {
            setupCurrency(it)
            position = it.size
        }

        val valueInputEditText = view.findViewById<TextInputEditText>(R.id.inputValue)
        handlingInput(valueInputEditText, view)

        val group = view.findViewById<Group>(R.id.group)
        group.setAllOnClickListener {
            when(checkingInternetConnection()) {
                true -> {
                    val addBottomSheet = AddBottomSheet(scroll = { scrollToBottom() })
                    addBottomSheet.show(childFragmentManager, null)
                }
                false -> noInternetSnackBar()
            }
        }
    }

    override fun startDragging(viewHolder: RecyclerView.ViewHolder) {
        itemTouchHelper.startDrag(viewHolder)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        when (menuStatus) {
            1 -> {
                menu.findItem(R.id.menu_sort).isVisible = true
                menu.findItem(R.id.menu_sort_cancel).isVisible = true
                menu.findItem(R.id.menu_delete_icon).isVisible = false
            }
            2 -> {
                menu.findItem(R.id.menu_sort).isVisible = false
                menu.findItem(R.id.menu_sort_cancel).isVisible = false
                menu.findItem(R.id.menu_delete_icon).isVisible = true
            }
        }

        when (chosenIndex) {
            0 -> menu.findItem(R.id.menu_sort_alphabetic)?.isChecked = true
            1 -> menu.findItem(R.id.menu_sort_cost)?.isChecked = true
            else -> Unit
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_sort_alphabetic -> {
                chosenIndex = sortCurrencies("alpha")
            }
            R.id.menu_sort_cost -> {
                chosenIndex = sortCurrencies("cost")
            }
            R.id.menu_sort_cancel -> {
                chosenIndex = cancelSort()
            }
            R.id.menu_delete_icon -> {
                showDeleteDialog()
            }
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun noInternetSnackBar() {
        val snackbar = Snackbar.make(
            snackbarView,
            "No Internet connection",
            Snackbar.LENGTH_LONG
        )
        snackbar.show()
    }

    private fun checkingInternetConnection(): Boolean {
        val connectivityManager = requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ->    true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ->   true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ->   true
                else -> {
                    false
                }
            }
        }else {
            if (connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnectedOrConnecting) {
                return true
            }
        }
        return false
    }

    private fun handlingInput(
        valueInputEditText: TextInputEditText,
        view: View
    ) {
        valueInputEditText.setOnEditorActionListener { _, keyCode, _ ->
            if (keyCode == EditorInfo.IME_ACTION_DONE) {
                inputValue = valueInputEditText.text.toString()
                if (inputValue.isBlank()) {
                    inputValue = "0"
                }

                val thread = HandlerThread("ThreadForCalculating")
                thread.start()
                val handler = Handler(thread.looper)
                handler.post {
                    viewModel.calculateConvertation()
                    Log.d("app", "thread: " + thread.name)
                }

                setupCurrency(addedCurrenciesList)
                view.hideKeyboard()
                convertationCounter += 1
                //updateConvertations(convertationCounter)
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    private fun showDeleteDialog() {
        deleteDialog.show(parentFragmentManager, null)
    }

    private fun removeSelectedItem(positionToRemove: Int) {
        lastDeletedItem = addedCurrenciesList[positionToRemove]
        lastDeletedPosition = positionToRemove
        currencyAdapter.removeItem(positionToRemove)
        addedCurrenciesList.removeAt(positionToRemove)
    }

    private fun returnDeletedItem() {
        currencyAdapter.addItemAt(lastDeletedPosition, lastDeletedItem)
        addedCurrenciesList.add(lastDeletedPosition, lastDeletedItem)
    }

    private fun changeToolbar(status: Int) {

        if (status == 2) {
            toolbar.title = getString(R.string.toolbar_title_delete)
            toolbar.background =
                ResourcesCompat.getDrawable(requireActivity().resources, R.drawable.toolbar_background_state2, null)
            toolbar.setTitleTextColor(ResourcesCompat.getColor(requireActivity().resources, R.color.black, null))
            menuStatus = 2
            requireActivity().invalidateOptionsMenu()
        } else {
            toolbar.title = getString(R.string.title)
            toolbar.background =
                ResourcesCompat.getDrawable(requireActivity().resources, R.drawable.toolbar_background_state1, null)
            toolbar.setTitleTextColor(ResourcesCompat.getColor(requireActivity().resources, R.color.text_title, null))
            menuStatus = 1
            requireActivity().invalidateOptionsMenu()
        }
    }

    private fun handlingLongClick(position: Int) {
        changeToolbar(2)
        deleteDialog =
            DeleteItemDialogFragment(removeSelectedItem = { removeSelectedItem(position) },
                changeToolbar = { changeToolbar(1) },
                undoSnackbar = { undoSnackbar() })
    }

    private fun undoSnackbar() {
        val snackbar = Snackbar.make(
            snackbarView,
            "Валюта удалена",
            Snackbar.LENGTH_SHORT
        )
        snackbar.setAction("Вернуть") {
            returnDeletedItem()
        }
        snackbar.show()
    }

    private fun sortCurrencies(option: String): Int {
        var chosenIndex = 0
        if (unSortedNameList.isEmpty())
            unSortedNameList.addAll(addedCurrenciesList)

        if (option == "alpha") {
            addedCurrenciesList.sortBy { it.name }
            chosenIndex = 0
        } else if (option == "cost") {
            addedCurrenciesList.sortBy { it.conversionRate }
            chosenIndex = 1
        }
        setupCurrency(addedCurrenciesList)
        return chosenIndex
    }

    private fun cancelSort(): Int {
        val tempList: MutableList<Currency> = mutableListOf()

        for (element in unSortedNameList) {
            if (addedCurrenciesList.contains(element)) {
                tempList.add(element)
            }
        }

        for (element in addedCurrenciesList) {
            if (!unSortedNameList.contains(element)) {
                tempList.add(element)
            }
        }

        addedCurrenciesList = tempList
        unSortedNameList.clear()
        setupCurrency(addedCurrenciesList)
        activity?.invalidateOptionsMenu()
        return 2
    }

    private fun setupCurrency(list: MutableList<Currency>) {
        Log.d("currency", "Setup adapter")

        currencyAdapter = CurrencyAdapter(
            this,
            handlingLongClick = { handlingLongClick(it) }
        )

        currencyManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        recyclerView.apply {
            adapter = currencyAdapter
            layoutManager = currencyManager
        }
        recyclerView.setHasFixedSize(true)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        currencyAdapter.setItems(list)
    }

    private fun Group.setAllOnClickListener(listener: View.OnClickListener?) {
        referencedIds.forEach { id ->
            view?.findViewById<View>(id)?.setOnClickListener(listener)
        }
    }

    private fun scrollToBottom() {
        val smoothScroller = object : LinearSmoothScroller(context) {
            override fun getVerticalSnapPreference(): Int = SNAP_TO_START
        }
        smoothScroller.targetPosition = position
        currencyManager.startSmoothScroll(smoothScroller)
    }
}