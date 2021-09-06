package com.example.statstracker

import java.util.*

abstract class News {
    private var _source: String? = "Source"
    private var _headline: String? = "Headline"
    private var _body: String? = "Body"
    private var _url: String? = "URL"

    private var _time: Date? = Date()
    private var _image: String? = "Image"

    fun getSource(): String? {
        return _source
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

    fun getTime(): Date? {
        return _time
    }

    fun getImage(): String? {
        return _image
    }

    fun setSource(source: String?) {
        _source = source
    }

    fun setHeadline(headline: String?) {
        _headline = headline
    }

    fun setBody(body: String?) {
        _body = body
    }

    fun setTime(time: Date?) {
        _time = time
    }

    fun setUrl(url: String?) {
        _url = url
    }

    fun setImage(image: String?) {
        _image = image
    }
}