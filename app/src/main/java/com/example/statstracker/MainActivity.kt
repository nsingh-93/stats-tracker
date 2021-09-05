package com.example.statstracker

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.*
import com.example.statstracker.newsapi.NewsResult
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var mainActivityAdapter: MainActivityAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    //NewsAPI
    private val API_KEY = "Your-API-Key"
    var gson = Gson()
    var stringRequest: StringRequest?  = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainActivityAdapter = MainActivityAdapter(this)
        viewPager = findViewById(R.id.pager)
        viewPager.adapter = mainActivityAdapter

        tabLayout = findViewById(R.id.tab_layout)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            if (position == 0){
                tab.text = "Newsfeed"
            } else {
                tab.text = "Scores"
            }
        }.attach()


        //----------------------------------------------------------------------------------------------

        //NewsAPI

        val q = "NHL+hockey"
        val date = Date()
        val formatter = SimpleDateFormat("yyyy-MM-dd",  Locale.getDefault())
        val dateAsString = formatter.format(date)
        val apiKey = "36c383a8d0b34b4292508eb66a1bd5ae"
        val url = "https://newsapi.org/v2/everything?q=$q&from=$dateAsString&apiKey=$apiKey&language=en"

// Instantiate the RequestQueue.
        val requestQueue = Volley.newRequestQueue(this)
        stringRequest = object : StringRequest(Request.Method.GET, url,
            Response.Listener { response ->
                //val precount = quoteList.count()

                val quoteJsonObject = JSONObject(response)

                val gsonparse = gson.fromJson(quoteJsonObject.toString(), NewsResult::class.java)

                Log.d("NEWS", gsonparse.articles[0].title)

                /*if(quoteJsonArray.length()>0){
                    mAdapter.notifyItemRangeChanged(precount,quoteList.count())


                }*/

                //tvStatus.text="Length = ${quoteJsonArray.length()}"

                requestQueue.stop()
            },
            Response.ErrorListener { error ->
                //tvStatus.text  = error.toString()
                Log.d("NEWS", "error => " + error.toString())
                requestQueue.stop()
            }
        ) {

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"]="Mozilla/5.0"
                return headers
            }
            /*@Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params = HashMap<String, String>()
                params["X-Mashape-Key"] = apiKey
                params["Accept"] = "application/json"
                return params
            }*/

        }

        // Add the request to the RequestQueue.
        requestQueue?.add(stringRequest)

    }

    class MainActivityAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            val fragmentNews = FragmentNewsFeed()
            val fragmentScores = FragmentScores()
            fragmentNews.arguments = Bundle().apply {
                putString("fragment", "FragmentNewsFeed")
            }
            fragmentScores.arguments = Bundle().apply {
                putString("fragment", "FragmentNewsFeed")
            }

            return if (position == 0){
                fragmentNews
            } else {
                fragmentScores
            }
        }
    }

}