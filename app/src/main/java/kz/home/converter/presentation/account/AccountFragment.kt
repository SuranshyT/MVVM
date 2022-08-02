package kz.home.converter.presentation.account

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.account_fragment.*
import kz.home.converter.R
import kz.home.converter.presentation.MainActivity

class AccountFragment(
    //val timeSpend: () -> Long
) : Fragment(R.layout.account_fragment) {
    lateinit var adapter: AccountPagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = AccountPagerAdapter(this
            //, timeSpend = { timeSpend() }
        )
        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager)
        viewPager.adapter = adapter
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Основные"
                1 -> tab.text = "Статистика"
                else -> tab.text = "Ещё один таб"
            }
        }.attach()
    }
}