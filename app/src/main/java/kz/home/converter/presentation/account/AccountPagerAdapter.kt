package kz.home.converter.presentation.account

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import kz.home.converter.presentation.TranslateFragment

class AccountPagerAdapter(activity: Fragment
                          //, val timeSpend: () -> Long
) :
    FragmentStateAdapter(activity) {
    private val fragments = mutableListOf<Fragment>()
    private val accountStatisticsFragment = AccountStatisticsFragment(
        //timeSpend = { timeSpend() }
    )

    init {
        fragments.add(AccountMainFragment())
        fragments.add(accountStatisticsFragment)
        fragments.add(TranslateFragment())
    }

    override fun createFragment(position: Int): Fragment =
        fragments[position]

    override fun getItemCount(): Int =
        fragments.size
}