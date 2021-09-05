package com.example.statstracker

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class FragmentNewsFeed : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var newsView: View = inflater.inflate(R.layout.frag_news, container, false)
        var newsRecyclerView: RecyclerView = newsView.findViewById(R.id.newsRecyclerView)

        val newsList: ArrayList<News> = ArrayList()

        for (i in 0 until 10) {
            newsList += News(
                "$i",
                "Headline $i",
                "Description $i",
                "https://www.nhl.com/",
                "9/4/2021",
                resources.getDrawable(R.drawable.test_image,null)
            )
        }

        var newsRecyclerAdapter: NewsFeedRecyclerAdapter = NewsFeedRecyclerAdapter(newsList, requireContext())
        return newsView
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {
        fun newInstance(text: String?) {
            val f = FragmentNewsFeed()
            val b = Bundle()
            b.putString("msg", text)
            f.arguments = b
        }
    }
}