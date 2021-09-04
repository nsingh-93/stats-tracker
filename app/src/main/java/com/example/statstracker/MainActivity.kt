package com.example.statstracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

// Fragments
        val fragmentNewsFeed = FragmentNewsFeed()
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.main_fragment, fragmentNewsFeed, "news_frag")
        fragmentTransaction.commitAllowingStateLoss()
        FragmentNewsFeed.newInstance("FirstFragment, Instance 1")
        //finish fragments


    }



}