package com.example.statstracker

import java.util.*

data class ScoresResult(
    var copyright:String,
    var totalItems:Int,
    var totalEvents:Int,
    var totalGames:Int,
    var totalMatches:Int,
    var metaData:MetaData,
    var wait:Int,
    var dates:List<ScoreDates>,

)

data class MetaData(
    var timeStamp:String
)

data class ScoreDates(
    var date:Date,
    var totalItems:Int,
    var totalEvents:Int,
    var totalGames:Int,
    var totalMatches:Int,
    var games:List<Games>,
    var events:List<Events>,
    var matches:List<Matches>
)

data class Games(
    var gamePK:String,
    var link:String,
    var gameType:String,
    var season:String,
    var gameDate:Date,
    var status:GameStatus,
    var teams:GameTeams,
    var venue:GameVenue,
    var content:GameContent
)

data class GameStatus(
    var abstractGameState:String,
    var codedGameState:String,
    var detailedState:String,
    var statusCode:String,
    var startTimeTBD:Boolean
)

data class GameTeams(
    var away:TeamStats,
    var home:TeamStats
)

data class TeamStats(
    var leagueRecord:LeagueRecord,
    var score:Int,
    var team:Team
)

data class LeagueRecord(
    var wins:Int,
    var losses:Int,
    var ot:Int,
    var leagueType:String
)

data class Team(
    var id:Int,
    var name:String,
    var link:String
)

data class GameVenue(
    var id:Int,
    var name:String,
    var link:String
)

data class GameContent(
    var link:String
)

data class Events(
    var eventType:String
)

data class Matches(
    var matchType:String
)

