package com.example.statstracker.newsapi

import java.util.*

data class Article(
    var source : Source,
    var author : String,
    var title : String,
    var description : String,
    var url : String,
    var urlToImage : String,
    var publishedAt : Date,
    var content: String)

data class Source(
    var id: String,
    var name: String
)