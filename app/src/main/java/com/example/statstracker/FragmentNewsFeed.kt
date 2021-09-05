package com.example.statstracker

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class FragmentNewsFeed : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val newsView: View = inflater.inflate(R.layout.frag_news, container, false)
        val newsRecyclerView: RecyclerView = newsView.findViewById(R.id.newsRecyclerView)

        val newsList: ArrayList<News> = ArrayList()

        for (i in 0 until 10) {
            val news = object : News("$i",
                "Headline $i",
                "Description $i",
                "https://www.nhl.com/",
                "9/4/2021",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/7/7a/Capitals-Maple_Leafs_%2834075134291%29.jpg/1200px-Capitals-Maple_Leafs_%2834075134291%29.jpg") {}

            newsList.add(news)
        }

        Log.i("NEWS",
            (if (newsList[0].getHeadline() != null) newsList[0].getHeadline() else "empty")!!
        )

        val newsRecyclerAdapter = NewsFeedRecyclerAdapter(newsList, requireContext())
        newsRecyclerView.adapter = newsRecyclerAdapter

        val linearLayoutManager = LinearLayoutManager(requireContext())
        newsRecyclerView.layoutManager = linearLayoutManager

        //newsRecyclerAdapter.notifyDataSetChanged()
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