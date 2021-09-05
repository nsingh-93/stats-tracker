package com.example.statstracker

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class FragmentNewsFeed : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val newsView: View = inflater.inflate(R.layout.frag_news, container, false)
        val newsRecyclerView: RecyclerView = newsView.findViewById(R.id.newsRecyclerView)

        val newsList: ArrayList<News> = ArrayList()

        for (i in 0 until 10) {
            val news = object : News() {}

            news.setHeadline("Headline $i")
            news.setBody("Description $i")
            news.setUrl("https://www.nhl.com/")
            news.setTime("9/4/2021")
            news.setImage("https://upload.wikimedia.org/wikipedia/commons/thumb/7/7a/Capitals-Maple_Leafs_%2834075134291%29.jpg/1200px-Capitals-Maple_Leafs_%2834075134291%29.jpg")

            newsList.add(news)
        }

        val newsRecyclerAdapter = NewsFeedRecyclerAdapter(newsList, requireContext())
        newsRecyclerView.adapter = newsRecyclerAdapter

        val linearLayoutManager = LinearLayoutManager(requireContext())
        newsRecyclerView.layoutManager = linearLayoutManager

        return newsView
    }

    companion object {
        fun newInstance(text: String?) {
            val f = FragmentNewsFeed()
            val b = Bundle()
            b.putString("fragment", text)
            f.arguments = b
        }
    }
}