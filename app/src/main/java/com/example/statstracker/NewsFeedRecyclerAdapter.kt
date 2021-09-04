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


class NewsFeedRecyclerAdapter internal constructor(
    listNews: List<News>,
    context: Context
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val listNews: List<News> = listNews
    private val context: Context = context
    private var timestampWidth = 0
    private val hourDateFormat = SimpleDateFormat("h:mm a", Locale.getDefault())

    //private SimpleDateFormat hour24DateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
    private val dayDateFormat = SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.getDefault())
    override fun getItemViewType(position: Int): Int {
        return VIEW_TYPE_NEWS_DEFAULT
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val defaultView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_item_article, parent, false)
        return UserDefaultHolder(defaultView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val newsItem: News = listNews[position]
        val headline: String = newsItem.getHeadline()
        val body: String = newsItem.getBody()
        val url: String = newsItem.getUrl()
        val timestamp: String = newsItem.getTime()
        val image: Image = newsItem.getImage()

        when (holder.itemViewType) {
            VIEW_TYPE_NEWS_DEFAULT -> (holder as UserDefaultHolder).bind(
                newsItem,
                headline,
                body,
                url,
                timestamp,
                image
            )
        }
    }

    //ViewHolders
    private inner class UserDefaultHolder internal constructor(v: View) :
        RecyclerView.ViewHolder(v) {
        var photoImageView: ImageView = v.findViewById(R.id.photoImageView)
        var newsCard: ConstraintLayout = v.findViewById(R.id.newsCard)
        var titleTextView: TextView = v.findViewById(R.id.titleTextView)
        var synopsisTextView: TextView = v.findViewById(R.id.synopsisTextView)
        var txtTimeStamp: TextView = v.findViewById(R.id.txtTimestamp)
        fun bind(
            newsItem: News,
            headline: String,
            body: String,
            url: String,
            timestamp: String,
            image: Image
        ) {
            txtTimeStamp.text = timestamp
            titleTextView.text = headline
            //photoImageView.setImageURI(null)
            synopsisTextView.text = body
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return listNews.size
    }

    companion object {
        //View Types
        private const val VIEW_TYPE_NEWS_DEFAULT = 0
    }
}