package com.example.statstracker

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.statstracker.newsapi.NewsResult
import com.google.gson.Gson
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class FragmentScores : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val gson = Gson()
        val stringRequest: StringRequest?

        val scoresView: View = inflater.inflate(R.layout.frag_scores, container, false)
        val scoresRecyclerView: RecyclerView = scoresView.findViewById(R.id.scoresRecyclerView)

        val scoresList: ArrayList<Scores> = ArrayList()

        for (i in 0 until 10) {
            val score = object : Scores() {}
        }

        val scoresRecyclerAdapter = ScoresRecyclerAdapter(scoresList, requireContext())
        scoresRecyclerView.adapter = scoresRecyclerAdapter

        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true
        scoresRecyclerView.layoutManager = linearLayoutManager

        val url = "https://statsapi.web.nhl.com/api/v1/schedule"

        val cal = Calendar.getInstance()
        val formatter = SimpleDateFormat("yyyy-MM-dd",  Locale.getDefault())
        val startDate = cal.add(Calendar.DATE, -1)
        val startDateAsString = "2021-07-05"//formatter.format(startDate)
        val endDate = cal.add(Calendar.DATE, 1)
        val endDateAsString = "2021-07-08"//formatter.format(endDate)

        val query = "$url?startDate=$startDateAsString&endDate=$endDateAsString"

        val requestQueue = Volley.newRequestQueue(requireContext())
        stringRequest = object : StringRequest(
            Method.GET, query,
            Response.Listener { response ->
                val scoresJsonObject = JSONObject(response)

                val gsonParse = gson.fromJson(scoresJsonObject.toString(), ScoresResult::class.java)

                var totalGames = gsonParse.totalGames

                Log.i("SCORESFrag", "Total Games: $totalGames")

                if(totalGames != 0){

                    for (dates in gsonParse.dates) {
                        for(game in dates.games) {
                            val score = object : Scores() {}

                            val home = game.teams.home
                            val away = game.teams.away

                            score.setHomeTeam(home.team.name)
                            score.setHomeTeamAbbr(CreateTeamAbbreviation(score.getHomeTeam()!!))
                            score.setHomeTeamScore(home.score)

                            val homeTeamStreak = home.leagueRecord.wins.toString() + "-" + home.leagueRecord.losses.toString() + "-" + home.leagueRecord.ot.toString()
                            score.setHomeTeamStreak(homeTeamStreak)

                            score.setAwayTeam(away.team.name)
                            score.setAwayTeamAbbr(CreateTeamAbbreviation(score.getAwayTeam()!!))
                            score.setAwayTeamScore(away.score)

                            val awayTeamStreak = away.leagueRecord.wins.toString() + "-" + away.leagueRecord.losses.toString() + "-" + away.leagueRecord.ot.toString()
                            score.setAwayTeamStreak(awayTeamStreak)

                            score.setGameDate(dates.date)
                            score.setGameStatus(game.status.abstractGameState)

                            Log.i("SCORES",game.status.abstractGameState + ", " + (game.status.abstractGameState == "Preview"))

                            scoresList.add(score)
                        }
                    }
                }

                scoresRecyclerAdapter.notifyDataSetChanged()

                requestQueue.stop()
            },
            Response.ErrorListener { error ->
                Log.d("NEWS", "error => $error")
                requestQueue.stop()
            }
        ) {

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"]="Mozilla/5.0"
                return headers
            }
        }

        // Add the request to the RequestQueue.
        //TODO Uncomment request below
        requestQueue.add(stringRequest)

        //-------------------------------------------------


        return scoresView
    }

    fun CreateTeamAbbreviation(teamName:String):String
    {
        var words = teamName.split(" ")

        return if (words.size == 2) {
            words[0].substring(0, 3).uppercase()
        } else {
            (words[0].substring(0, 1) + words[1].substring(0, 1) + words[2].substring(0, 1)).uppercase()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {
        fun newInstance(text: String?) {
            val f = FragmentScores()
            val b = Bundle()
            b.putString("fragment", text)
            f.arguments = b
        }
    }
}