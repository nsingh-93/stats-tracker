package com.example.statstracker

import android.media.Image

class News {
    private lateinit var _id: String
    private lateinit var _headline: String
    private lateinit var _body: String
    private lateinit var _url: String

    private lateinit var _time: String
    private lateinit var _image: Image

    fun getId(): String {
        return _id
    }

    fun getHeadline(): String {
        return _headline
    }

    fun getBody(): String {
        return _body
    }

    fun getUrl(): String {
        return _url
    }

    fun getTime(): String {
        return _time
    }

    fun getImage(): Image {
        return _image
    }

    fun setId(id: String) {
        _id = id
    }

    fun setHeadline(headline: String) {
        _headline = headline
    }

    fun setBody(body: String) {
        _body = body
    }

    fun setTime(time: String) {
        _time = time
    }

    fun setUrl(url: String) {
        _url = url
    }

    fun setImage(image: Image) {
        _image = image
    }
}