package com.example.statstracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var mainActivityAdapter: MainActivityAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

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