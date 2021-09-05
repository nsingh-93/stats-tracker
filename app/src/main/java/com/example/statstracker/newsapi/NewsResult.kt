package com.example.statstracker.newsapi

import java.util.*

data class NewsResult(
    var status:String,
    var totalResults:String,
    var articles:List<Article>)