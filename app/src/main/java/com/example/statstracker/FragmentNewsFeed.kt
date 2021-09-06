package com.example.statstracker

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.statstracker.newsapi.NewsResult
import com.google.gson.Gson
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class FragmentNewsFeed : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val gson = Gson()
        val stringRequest: StringRequest?

        val newsView: View = inflater.inflate(R.layout.frag_news, container, false)
        val newsRecyclerView: RecyclerView = newsView.findViewById(R.id.newsRecyclerView)

        val newsList: ArrayList<News> = ArrayList()

        val newsRecyclerAdapter = NewsFeedRecyclerAdapter(newsList, requireContext())
        newsRecyclerView.adapter = newsRecyclerAdapter

        val linearLayoutManager = LinearLayoutManager(requireContext())
        newsRecyclerView.layoutManager = linearLayoutManager


        //----------------------------------------------------------------------------------------------

        //NewsAPI

        val q = "NHL+hockey"

        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, -2)
        val date = cal.time
        val formatter = SimpleDateFormat("yyyy-MM-dd",  Locale.getDefault())
        val dateAsString = formatter.format(date)
        val apiKey = "36c383a8d0b34b4292508eb66a1bd5ae"
        val url = "https://newsapi.org/v2/everything?q=$q&from=$dateAsString&sortBy=publishedAt&language=en&apiKey=$apiKey"

// Instantiate the RequestQueue.
        val requestQueue = Volley.newRequestQueue(requireContext())
        stringRequest = object : StringRequest(
            Method.GET, url,
            Response.Listener { response ->
                //val precount = quoteList.count()

                val newsJsonObject = JSONObject(response)

                val gsonParse = gson.fromJson(newsJsonObject.toString(), NewsResult::class.java)

                if(gsonParse.articles.isNotEmpty()){
                    val numItems: Int = if (gsonParse.articles.size <= 10) {
                        gsonParse.articles.size
                    } else {
                        10
                    }

                    for (i in 0 until numItems) {
                        val news = object : News() {}

                        news.setSource(gsonParse.articles[i].source.name)
                        news.setHeadline(gsonParse.articles[i].title)
                        news.setBody(gsonParse.articles[i].description)
                        news.setUrl(gsonParse.articles[i].url)
                        news.setTime(gsonParse.articles[i].publishedAt)
                        news.setImage(gsonParse.articles[i].urlToImage)

                        newsList.add(news)
                    }
                }

                newsRecyclerAdapter.notifyDataSetChanged()

                requestQueue.stop()
            },
            Response.ErrorListener { error ->
                Log.d("NEWS", "error => $error")
                requestQueue.stop()
            }
        ) {

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"]="Mozilla/5.0"
                return headers
            }
        }

        // Add the request to the RequestQueue.
        //TODO Uncomment request below
        //requestQueue.add(stringRequest)

        //-------------------------------------------------

        return newsView
    }

/*    companion object {
        fun newInstance(text: String?) {
            val f = FragmentNewsFeed()
            val b = Bundle()
            b.putString("fragment", text)
            f.arguments = b
        }
    }*/
}