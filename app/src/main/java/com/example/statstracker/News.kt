package com.example.statstracker

import android.media.Image

class News {
    private var _id: String? = null
    private var _headline: String? = null
    private var _body: String? = null
    private var _url: String? = null

    private lateinit var _time: String
    private var _image: Image? = null

    fun getId(): String? {
        return _id
    }

    fun getHeadline(): String? {
        return _headline
    }

    fun getBody(): String? {
        return _body
    }

    fun getUrl(): String? {
        return _url
    }

    fun getTime(): String {
        return _time
    }

    fun getImage(): Image? {
        return _image
    }

    fun setId(id: String?) {
        _id = id
    }

    fun setHeadline(headline: String?) {
        _headline = headline
    }

    fun setBody(body: String?) {
        _body = body
    }

    fun setTime(time: String) {
        _time = time
    }

    fun setUrl(url: String?) {
        _url = url
    }

    fun setImage(image: Image?) {
        _image = image
    }
}