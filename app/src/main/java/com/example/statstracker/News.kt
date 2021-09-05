package com.example.statstracker

import android.media.Image

abstract class News /*internal constructor(
    _id: String?,
    _headline: String?,
    _body: String?,
    _url: String?,

    _time: String?,
    _image: String?
)*/{
    private var _headline: String? = "test"
    private var _body: String? = "test"
    private var _url: String? = "test"

    private var _time: String? = "test"
    private var _image: String? = "test"

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