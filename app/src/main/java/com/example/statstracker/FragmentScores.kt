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
import java.util.ArrayList

class FragmentScores : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val scoresView: View = inflater.inflate(R.layout.frag_scores, container, false)
        val scoresRecyclerView: RecyclerView = scoresView.findViewById(R.id.scoresRecyclerView)

        val scoresList: ArrayList<Scores> = ArrayList()

        for (i in 0 until 10) {
            val score = object : Scores() {}
        }

        val scoresRecyclerAdapter = ScoresRecyclerAdapter(scoresList, requireContext())
        scoresRecyclerView.adapter = scoresRecyclerAdapter

        val linearLayoutManager = LinearLayoutManager(requireContext())
        scoresRecyclerView.layoutManager = linearLayoutManager

        return scoresView
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