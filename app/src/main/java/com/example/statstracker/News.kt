package com.example.statstracker

import android.media.Image

abstract class News {
    private var _headline: String? = "Headline"
    private var _body: String? = "Body"
    private var _url: String? = "URL"

    private var _time: String? = "Time"
    private var _image: String? = "Image"

    fun getHeadline(): String? {
        return _headline
    }

    fun getBody(): String? {
        return _body
    }

    fun getUrl(): String? {
        return _url
    }

    fun getTime(): String? {
        return _time
    }

    fun getImage(): String? {
        return _image
    }

    fun setHeadline(headline: String?) {
        _headline = headline
    }

    fun setBody(body: String?) {
        _body = body
    }

    fun setTime(time: String?) {
        _time = time
    }

    fun setUrl(url: String?) {
        _url = url
    }

    fun setImage(image: String?) {
        _image = image
    }
}