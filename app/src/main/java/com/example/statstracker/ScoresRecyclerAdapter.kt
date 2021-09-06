package com.example.statstracker

import android.content.Context
import android.graphics.Color
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*
import android.graphics.Bitmap

import android.graphics.BitmapFactory

import android.widget.Toast

import androidx.test.core.app.ApplicationProvider.getApplicationContext

import android.os.AsyncTask
import android.util.Log
import android.view.ViewTreeObserver
import androidx.test.core.app.ApplicationProvider
import java.io.InputStream
import java.lang.Exception
import java.net.URL

class ScoresRecyclerAdapter internal constructor(
    listScores: List<Scores>,
    context: Context
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val listScores: List<Scores> = listScores
    private val context: Context = context
    private val dayDateFormat = SimpleDateFormat("EEEE, MMMM dd", Locale.getDefault())

    override fun getItemViewType(position: Int): Int =
        when {
            listScores[position].getGameStatus() == "Final" -> VIEW_TYPE_SCORE_FINISHED
            listScores[position].getGameStatus() == "Live" -> VIEW_TYPE_SCORE_PROGRESS
            listScores[position].getGameStatus() == "Preview" -> VIEW_TYPE_SCORE_UPCOMING
            else -> VIEW_TYPE_SCORE_UPCOMING
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_SCORE_FINISHED -> {
                val scoreFinishedView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycler_item_scores, parent, false)
                ScoreFinishedHolder(scoreFinishedView)
            }
            VIEW_TYPE_SCORE_PROGRESS -> {
                val scoreProgressView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycler_item_upcoming_games, parent, false)
                ScoreProgressHolder(scoreProgressView)
            }
            VIEW_TYPE_SCORE_UPCOMING -> {
                val scoreUpcomingView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycler_item_upcoming_games, parent, false)
                ScoreUpcomingHolder(scoreUpcomingView)
            }
            else -> {
                val defaultView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycler_item_upcoming_games, parent, false)
                ScoreUpcomingHolder(defaultView)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val score: Scores = listScores[position]
        val homeTeam: String? = score.getHomeTeam()
        val homeTeamAbbr: String? = score.getHomeTeamAbbr()
        val homeTeamScore: Int? = score.getHomeTeamScore()
        val homeTeamStreak: String? =score.getHomeTeamStreak()
        val awayTeam: String? = score.getAwayTeam()
        val awayTeamAbbr: String? = score.getAwayTeamAbbr()
        val awayTeamScore: Int? = score.getAwayTeamScore()
        val awayTeamStreak: String? = score.getAwayTeamStreak()
        val homeTeamLogo: String? = score.getHomeLogoSrc()
        val awayTeamLogo: String? = score.getAwayLogoSrc()
        val gameDate = dayDateFormat.format(score.getGameDate())

        when (holder.itemViewType) {
            ScoresRecyclerAdapter.VIEW_TYPE_SCORE_FINISHED -> (holder as ScoresRecyclerAdapter.ScoreFinishedHolder).bind(
                score,
                homeTeam,
                homeTeamAbbr,
                homeTeamScore,
                homeTeamStreak,
                awayTeam,
                awayTeamAbbr,
                awayTeamScore,
                awayTeamStreak,
                homeTeamLogo,
                awayTeamLogo,
                gameDate
            )
            ScoresRecyclerAdapter.VIEW_TYPE_SCORE_PROGRESS -> (holder as ScoresRecyclerAdapter.ScoreProgressHolder).bind(
                score,
                homeTeam,
                homeTeamAbbr,
                homeTeamStreak,
                awayTeam,
                awayTeamAbbr,
                awayTeamStreak,
                homeTeamLogo,
                awayTeamLogo,
                gameDate
            )
            ScoresRecyclerAdapter.VIEW_TYPE_SCORE_UPCOMING -> (holder as ScoresRecyclerAdapter.ScoreUpcomingHolder).bind(
                score,
                homeTeam,
                homeTeamAbbr,
                homeTeamStreak,
                awayTeam,
                awayTeamAbbr,
                awayTeamStreak,
                homeTeamLogo,
                awayTeamLogo,
                gameDate
            )
        }
    }

    private inner class ScoreFinishedHolder internal constructor(v: View) :
        RecyclerView.ViewHolder(v) {
        var imageAwayTeam: ImageView = v.findViewById(R.id.imageAwayTeam)
        var imageHomeTeam: ImageView = v.findViewById(R.id.imageHomeTeam)
        var textAwayAbbr: TextView = v.findViewById(R.id.textAwayAbbreviation)
        var textHomeAbbr: TextView = v.findViewById(R.id.textHomeAbbreviation)
        var textAwayScore: TextView = v.findViewById(R.id.textAwayScore)
        var textHomeScore: TextView = v.findViewById(R.id.textHomeScore)
        var textAwayStreak: TextView = v.findViewById(R.id.textAwayStreak)
        var textHomeStreak: TextView = v.findViewById(R.id.textHomeStreak)
        var textGameDate: TextView = v.findViewById(R.id.textGameDate)
        fun bind(
            scoreItem: Scores,
            homeTeam: String?,
            homeTeamAbbr: String?,
            homeTeamScore: Int?,
            homeTeamStreak: String?,
            awayTeam: String?,
            awayTeamAbbr: String?,
            awayTeamScore: Int?,
            awayTeamStreak: String?,
            homeTeamLogo: String?,
            awayTeamLogo: String?,
            gameDate: String?
        ) {
            textHomeAbbr.text = homeTeamAbbr
            textHomeScore.text = homeTeamScore.toString()
            textHomeStreak.text = homeTeamStreak
            textAwayAbbr.text = awayTeamAbbr
            textAwayScore.text = awayTeamScore.toString()
            textAwayStreak.text = awayTeamStreak
            textGameDate.text = gameDate
            //imageAwayTeam.setImageResource()
        }
    }

    private inner class ScoreProgressHolder internal constructor(v: View) :
        RecyclerView.ViewHolder(v) {
        var imageAwayTeam: ImageView = v.findViewById(R.id.imageAwayTeam)
        var imageHomeTeam: ImageView = v.findViewById(R.id.imageHomeTeam)
        var textAwayAbbr: TextView = v.findViewById(R.id.textAwayAbbreviation)
        var textHomeAbbr: TextView = v.findViewById(R.id.textHomeAbbreviation)
        var textAwayStreak: TextView = v.findViewById(R.id.textAwayStreak)
        var textHomeStreak: TextView = v.findViewById(R.id.textHomeStreak)
        var textGameDate: TextView = v.findViewById(R.id.textGameDate)
        fun bind(
            scoreItem: Scores,
            homeTeam: String?,
            homeTeamAbbr: String?,
            homeTeamStreak: String?,
            awayTeam: String?,
            awayTeamAbbr: String?,
            awayTeamStreak: String?,
            homeTeamLogo: String?,
            awayTeamLogo: String?,
            gameDate: String?
        ) {
            textHomeAbbr.text = homeTeamAbbr
            textHomeStreak.text = homeTeamStreak
            textAwayAbbr.text = awayTeamAbbr
            textAwayStreak.text = awayTeamStreak
            textGameDate.text = gameDate
            //imageAwayTeam.setImageResource()
        }
    }

    private inner class ScoreUpcomingHolder internal constructor(v: View) :
        RecyclerView.ViewHolder(v) {
        var imageAwayTeam: ImageView = v.findViewById(R.id.imageAwayTeam)
        var imageHomeTeam: ImageView = v.findViewById(R.id.imageHomeTeam)
        var textAwayAbbr: TextView = v.findViewById(R.id.textAwayAbbreviation)
        var textHomeAbbr: TextView = v.findViewById(R.id.textHomeAbbreviation)
        var textAwayStreak: TextView = v.findViewById(R.id.textAwayStreak)
        var textHomeStreak: TextView = v.findViewById(R.id.textHomeStreak)
        var textGameDate: TextView = v.findViewById(R.id.textGameDate)
        fun bind(
            scoreItem: Scores,
            homeTeam: String?,
            homeTeamAbbr: String?,
            homeTeamStreak: String?,
            awayTeam: String?,
            awayTeamAbbr: String?,
            awayTeamStreak: String?,
            homeTeamLogo: String?,
            awayTeamLogo: String?,
            gameDate: String?
        ) {
            textHomeAbbr.text = homeTeamAbbr
            textHomeStreak.text = homeTeamStreak
            textAwayAbbr.text = awayTeamAbbr
            textAwayStreak.text = awayTeamStreak
            textGameDate.text = gameDate
            //imageAwayTeam.setImageResource()
        }
    }

    override fun getItemCount(): Int {
        return listScores.size
    }

    companion object {
        private const val VIEW_TYPE_SCORE_FINISHED = 0
        private const val VIEW_TYPE_SCORE_PROGRESS = 1
        private const val VIEW_TYPE_SCORE_UPCOMING = 2
    }
}
