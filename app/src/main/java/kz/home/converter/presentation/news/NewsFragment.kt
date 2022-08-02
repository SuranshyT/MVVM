package kz.home.converter.presentation.news

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.news_fragment.*
import kz.home.converter.domain.News
import kz.home.converter.R
import kz.home.converter.utils.newsList

class NewsFragment : Fragment(R.layout.news_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cancelButton = view.findViewById<ImageView>(R.id.cancel_button)
        cancelButton.setOnClickListener {
            motionLayout.setTransition(R.id.thirdTransition)
            motionLayout.transitionToEnd() {
                motionLayout.setTransition(R.id.firstTransition)
            }
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.news_recycler_view)
        val newsAdapter = NewsAdapter(onClick = { onClick(view, it) })
        val newsManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        recyclerView.adapter = newsAdapter
        recyclerView.layoutManager = newsManager
        newsAdapter.setItems(newsList)
    }

    private fun onClick(
        view: View, item: News
    ) {
        motionLayout.setTransition(R.id.firstTransition)
        motionLayout.jumpToState(R.id.start)
        Glide.with(view).load(item.img).centerCrop().into(img)
        title.text = item.title
        info.text = item.details
        motionLayout.transitionToEnd() {
            motionLayout.setTransition(R.id.secondTransition)
        }
    }
}