package com.example.statstracker

import android.graphics.drawable.Drawable
import java.util.*

abstract class Scores {
    private var _homeTeam: String? = "Home Team"
    private var _homeTeamAbbr: String? = "HOM"
    private var _homeTeamScore: Int? = 0
    private var _homeTeamStreak: String? = "0-0-0"
    private var _homeLogoSrc: String? = "@tools:sample/avatars"

    private var _awayTeam: String? = "Away Team"
    private var _awayTeamAbbr: String? = "AWA"
    private var _awayTeamScore: Int? = 0
    private var _awayTeamStreak: String? = "0-0-0"
    private var _awayLogoSrc: String? = "@tools:sample/avatars"

    private var _gameDate: Date? = Date()

    fun getHomeTeam(): String? {
        return _homeTeam
    }

    fun getHomeTeamAbbr(): String? {
        return _homeTeamAbbr
    }

    fun getHomeTeamScore(): Int? {
        return _homeTeamScore
    }

    fun getHomeTeamStreak(): String? {
        return _homeTeamStreak
    }

    fun getHomeLogoSrc(): String? {
        return _homeLogoSrc
    }

    fun getAwayTeam(): String? {
        return _awayTeam
    }

    fun getAwayTeamAbbr(): String? {
        return _awayTeamAbbr
    }

    fun getAwayTeamScore(): Int? {
        return _awayTeamScore
    }

    fun getAwayTeamStreak(): String? {
        return _awayTeamStreak
    }

    fun getAwayLogoSrc(): String? {
        return _awayLogoSrc
    }

    fun getGameDate(): Date? {
        return _gameDate
    }

    fun setHomeTeam(homeTeam: String?) {
        _homeTeam = homeTeam
    }

    fun setHomeTeamAbbr(homeTeamAbbr: String?) {
        _homeTeamAbbr = homeTeamAbbr
    }

    fun setHomeTeamScore(homeTeamScore: Int?) {
        _homeTeamScore = homeTeamScore
    }

    fun setHomeTeamStreak(homeTeamStreak: String?) {
        _homeTeamStreak = homeTeamStreak
    }

    fun setHomeLogoSrc(homeLogoSrc: String?) {
        _homeLogoSrc = homeLogoSrc
    }

    fun setAwayTeam(awayTeam: String?) {
        _awayTeam = awayTeam
    }

    fun setAwayTeamAbbr(awayTeamAbbr: String?) {
        _awayTeamAbbr = awayTeamAbbr
    }

    fun setAwayTeamScore(awayTeamScore: Int?) {
        _awayTeamScore = awayTeamScore
    }

    fun setAwayTeamStreak(awayTeamStreak: String?) {
        _awayTeamStreak = awayTeamStreak
    }

    fun setAwayLogoSrc(awayLogoSrc: String?) {
        _awayLogoSrc = awayLogoSrc
    }

    fun setGameDate(gameDate: Date?) {
        _gameDate = gameDate
    }
}