package com.example.statstracker

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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
        val source: String? = newsItem.getSource()
        val headline: String? = newsItem.getHeadline()
        val body: String? = newsItem.getBody()
        val url: String? = newsItem.getUrl()
        val timestamp: Date? = newsItem.getTime()
        val image: String? = newsItem.getImage()

        when (holder.itemViewType) {
            VIEW_TYPE_NEWS_DEFAULT -> (holder as UserDefaultHolder).bind(
                newsItem,
                source,
                headline,
                body,
                url,
                timestamp,
                image
            )
        }

        holder.itemView.setOnClickListener {
            val appsIntent = Intent(Intent.ACTION_VIEW)
            appsIntent.data = Uri.parse(url)
            context.startActivity(appsIntent)
        }
    }

    //ViewHolders
    private inner class UserDefaultHolder internal constructor(v: View) :
        RecyclerView.ViewHolder(v) {
        var txtSource: TextView = v.findViewById(R.id.txtSource)
        var photoImageView: ImageView = v.findViewById(R.id.photoImageView)
        var titleTextView: TextView = v.findViewById(R.id.titleTextView)
        var synopsisTextView: TextView = v.findViewById(R.id.synopsisTextView)
        var txtTimeStamp: TextView = v.findViewById(R.id.txtTimestamp)
        fun bind(
            newsItem: News,
            source: String?,
            headline: String?,
            body: String?,
            url: String?,
            timestamp: Date?,
            image: String?
        ) {
            val formatter = SimpleDateFormat("MMM dd, yyyy",  Locale.getDefault())
            val dateAsString = formatter.format(timestamp)

            txtTimeStamp.text = dateAsString
            titleTextView.text = headline
            //photoImageView.setImageURI(null)
            synopsisTextView.text = body

            txtSource.text = source

            DownloadImageFromInternet(photoImageView).execute(image)

        }
    }

    //Get image from URL
    //TODO - Add function to grab image and get either bitmap or URI to be used for news image
    @SuppressLint("StaticFieldLeak")
    @Suppress("DEPRECATION")
    private inner class DownloadImageFromInternet(var imageView: ImageView) : AsyncTask<String, Void, Bitmap?>() {
        init {
        }
        override fun doInBackground(vararg urls: String): Bitmap? {
            val imageURL = urls[0]
            var image: Bitmap? = null
            try {
                val `in` = java.net.URL(imageURL).openStream()
                image = BitmapFactory.decodeStream(`in`)
            }
            catch (e: Exception) {
                Log.e("Error Message", e.message.toString())
                e.printStackTrace()
            }
            return image
        }
        override fun onPostExecute(result: Bitmap?) {
            imageView.setImageBitmap(result)
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